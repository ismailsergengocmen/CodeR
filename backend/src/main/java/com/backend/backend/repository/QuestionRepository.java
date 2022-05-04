package com.backend.backend.repository;

import com.backend.backend.entity.*;
import com.backend.backend.util.IntWithString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
public class QuestionRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Question> questionRowMapper = (rs, rowNum) -> {
        Question question = new Question();
        question.setQuestion_id(rs.getInt("question_id"));
        question.setUser_id(rs.getInt("user_id"));
        question.setCreate_date(rs.getTimestamp("create_date").toLocalDateTime());
        question.setQuestion_difficulty(rs.getInt("question_difficulty"));
        question.setQuestion_title(rs.getString("question_title"));
        question.setQuestion_content(rs.getString("question_content"));

        return question;
    };

    RowMapper<Challenge> challengeRowMapper = (rs, rowNum) -> {
        Challenge challenge = new Challenge();
        challenge.setQuestion_id(rs.getInt("question_id"));
        challenge.setUser_id(rs.getInt("user_id"));
        challenge.setCreate_date(rs.getTimestamp("create_date").toLocalDateTime());
        challenge.setQuestion_difficulty(rs.getInt("question_difficulty"));
        challenge.setQuestion_title(rs.getString("question_title"));
        challenge.setQuestion_content(rs.getString("question_content"));

        return challenge;
    };

    RowMapper<String> stringMapper = (rs, rowNum) -> rs.getString(1);

    RowMapper<TestCase> testCaseMapper = (rs, rowNum) -> {
        TestCase testCase = new TestCase();
        testCase.setQuestion_id(rs.getInt("question_id"));
        testCase.setTest_case_id(rs.getInt("test_case_id"));
        testCase.setInput(rs.getString("input"));
        testCase.setOutput(rs.getString("output"));
        testCase.setTest_case_type(rs.getString("testcase_type"));

        return testCase;
    };

    RowMapper<IntWithString> categoryMapper = (rs, rowNum) -> {
        IntWithString intWithString = new IntWithString();
        intWithString.setInt_part(rs.getInt("question_id"));
        intWithString.setString_part(rs.getString("category"));

        return intWithString;
    };

    RowMapper<NonCodingQuestion> nonCodingRowMapper = (rs, rowNum) -> {
        NonCodingQuestion nonCodingQuestion = new NonCodingQuestion();
        nonCodingQuestion.setQuestion_id(rs.getInt("question_id"));
        nonCodingQuestion.setUser_id(rs.getInt("user_id"));
        nonCodingQuestion.setCreate_date(rs.getTimestamp("create_date").toLocalDateTime());
        nonCodingQuestion.setQuestion_difficulty(rs.getInt("question_difficulty"));
        nonCodingQuestion.setQuestion_title(rs.getString("question_title"));
        nonCodingQuestion.setQuestion_content(rs.getString("question_content"));

        return nonCodingQuestion;
    };

    public List<Question> getAllQuestions(List<String> category, List<Integer> difficulty) {
        String sql = "SELECT question_id FROM question";
        List<Object> ids = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("question_id"));
        List<Question> questionList;

        // Check if there is any category limitation
        if (category != null) {
            // For each category, first find the questions that have that category and apply the other categories to those questions.
            // Repeat this until there is no more question or categories.
            for (String s : category) {
                sql = "SELECT question_id FROM question NATURAL JOIN question_category WHERE category = ?";
                String questionMarks = StringUtils.repeat("?", ", ", ids.size());
                String tempSQL = sql + " AND question_id IN (" + questionMarks + ")";

                ids.add(0, s);
                Object[] paramArray = ids.toArray();
                ids = jdbcTemplate.query(tempSQL, (rs, rowNum) -> rs.getInt("question_id"), paramArray);

                // To prevent the next iterations since there is no question with all the previous categories
                if (ids.size() == 0) {
                    break;
                }
            }

            if (ids.size() == 0) {
                return new ArrayList<>();
            } else {
                sql = "SELECT question_id, user_id, create_date, question_difficulty, question_title, question_content FROM question WHERE question_id IN ";
                sql = sql + "(" + StringUtils.repeat("?", ", ", ids.size()) + ")";

                // Check if there is any difficulty filter
                if(difficulty != null) {
                    sql = sql + " AND question_difficulty IN (" + StringUtils.repeat("?", ", ", difficulty.size()) + ")";
                    ids.addAll(difficulty);
                }
                questionList = jdbcTemplate.query(sql, questionRowMapper, ids.toArray());
            }
        }
        else {
          sql = "SELECT question_id, user_id, create_date, question_difficulty, question_title, question_content FROM question";

          // Check if there is any difficulty filter
          if (difficulty != null) {
              sql = sql + " WHERE question_difficulty IN (" + StringUtils.repeat("?", ", ", difficulty.size()) + ")";
              questionList = jdbcTemplate.query(sql, questionRowMapper, difficulty.toArray());
          } else {
              questionList = jdbcTemplate.query(sql, questionRowMapper);
          }
        }

        // Find all question's categories and put them into a hashmap
        HashMap<Integer, List<String>> categories = categoryMapper("question");

        // For the questionList derived after filtering categories and difficulty, add all of their categories
        for (Question q: questionList) {
            q.setQuestion_category(categories.get(q.getQuestion_id()));
        }
        return questionList;
    }

    public Question getQuestionWithId(int question_id) {
        String sql = "SELECT question_id, user_id, create_date, question_difficulty, question_title, question_content FROM question WHERE question_id = ?";
        try {
            Question question = jdbcTemplate.queryForObject(sql, questionRowMapper, question_id);

            // Add the question categories
            sql = "SELECT category FROM question_category WHERE question_id = " + question_id;
            List<String> categories = jdbcTemplate.query(sql, stringMapper);
            question.setQuestion_category(categories);

            return question;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Challenge> getAllChallenges() {
        String sql = "SELECT question_id, user_id, create_date, question_difficulty, question_title, question_content FROM question NATURAL JOIN challenge";
        List<Challenge> challengeList = jdbcTemplate.query(sql, challengeRowMapper);

        // Find all challenges' categories and put them into a hashmap
        HashMap<Integer, List<String>> categories = categoryMapper("challenge");
        for (Challenge c: challengeList) {
            c.setQuestion_category(categories.get(c.getQuestion_id()));
        }

        return challengeList;
    }

    public Challenge getChallengeWithId(int question_id) {
        try {
            String sql = "SELECT question_id, user_id, create_date, question_difficulty, question_title, question_content FROM question NATURAL JOIN challenge WHERE question_id = ?";
            Challenge challenge = jdbcTemplate.queryForObject(sql, challengeRowMapper, question_id);

            // Add the question categories
            sql = "SELECT category FROM question_category WHERE question_id = " + question_id;
            List<String> categories = jdbcTemplate.query(sql, stringMapper);
            challenge.setQuestion_category(categories);

            // Add the hints
            sql = "SELECT hint FROM challenge_hint WHERE question_id = " + question_id;
            List<String> hints = jdbcTemplate.query(sql, stringMapper);
            challenge.setHints(hints);

            // Add the test cases
            sql = "SELECT question_id, test_case_id, input, output, testcase_type FROM test_case WHERE question_id = " + question_id;
            List<TestCase> testCases = jdbcTemplate.query(sql, testCaseMapper);
            challenge.setTest_cases(testCases);

            return challenge;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<NonCodingQuestion> getAllNonCodingChallenges() {
        String sql = "SELECT question_id, user_id, create_date, question_difficulty, question_title, question_content FROM question NATURAL JOIN non_coding_question";
        List<NonCodingQuestion> nonCodingQuestion = jdbcTemplate.query(sql, nonCodingRowMapper);

        // Find all non_coding_questions' categories and put them into a hashmap
        HashMap<Integer, List<String>> categories = categoryMapper("non_coding_question");
        for (NonCodingQuestion c: nonCodingQuestion) {
            c.setQuestion_category(categories.get(c.getQuestion_id()));
        }

        return nonCodingQuestion;
    }

    public NonCodingQuestion getNonCodingQuestionWithId(int question_id) {
        String sql = "SELECT question_id, user_id, create_date, question_difficulty, question_title, question_content FROM question NATURAL JOIN non_coding_question WHERE question_id = ?";
         try {
             NonCodingQuestion nonCodingQuestion = jdbcTemplate.queryForObject(sql, nonCodingRowMapper, question_id);

             // Add the question categories
             sql = "SELECT category FROM question_category WHERE question_id = " + question_id;
             List<String> categories = jdbcTemplate.query(sql, stringMapper);
             nonCodingQuestion.setQuestion_category(categories);

             return nonCodingQuestion;
         } catch(EmptyResultDataAccessException e) {
             return null;
         }
    }

    public Integer createChallenge(Challenge challenge) throws SQLException {
        try {
            // Insert the question into question table
            String sql = "INSERT INTO question (user_id, create_date, question_difficulty, question_title, question_content) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, challenge.getUser_id(), challenge.getCreate_date(), challenge.getQuestion_difficulty(), challenge.getQuestion_title(), challenge.getQuestion_content());

            // Get the question_id of the newly inserted question
            int question_id = jdbcTemplate.queryForObject("SELECT question_id FROM question WHERE user_id = ? AND create_date = ?", int.class, challenge.getUser_id(), challenge.getCreate_date());

            // Insert the question into challenge table
            jdbcTemplate.update("INSERT INTO challenge (question_id) VALUES (?)", question_id);

            // Insert the categories into question_category table
            if (challenge.getQuestion_category()!= null && challenge.getQuestion_category().size() != 0) {
                for (String category: challenge.getQuestion_category()) {
                    jdbcTemplate.update("INSERT INTO question_category (question_id, category) VALUES (?, ?)", question_id, category);
                }
            }

            // Insert the hints into challenge_hint table
            if (challenge.getHints() != null && challenge.getHints().size() != 0) {
                for (String hint: challenge.getHints()) {
                    jdbcTemplate.update("INSERT INTO challenge_hint (question_id, hint) VALUES (?, ?)", question_id, hint);
                }
            }

            // Insert the test cases into test_case table
            for (TestCase t: challenge.getTest_cases()) {
                jdbcTemplate.update("INSERT INTO test_case (question_id, input, output, testcase_type) VALUES (?, ?, ?, ?)", question_id, t.getInput(), t.getOutput(), t.getTest_case_type());
            }

            return question_id;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public Integer createNonCodingQuestion(NonCodingQuestion nonCodingQuestion) throws SQLException {
        try {
            // Insert the question into question table
            String sql = "INSERT INTO question (user_id, create_date, question_difficulty, question_title, question_content) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql, nonCodingQuestion.getUser_id(), nonCodingQuestion.getCreate_date(), nonCodingQuestion.getQuestion_difficulty(), nonCodingQuestion.getQuestion_title(), nonCodingQuestion.getQuestion_content());

            // Get the question_id of the newly inserted question
            int question_id = jdbcTemplate.queryForObject("SELECT question_id FROM question WHERE user_id = ? AND create_date = ?", int.class, nonCodingQuestion.getUser_id(), nonCodingQuestion.getCreate_date());

            // Insert the question into non_coding_question table
            jdbcTemplate.update("INSERT INTO non_coding_question (question_id) VALUES (?)", question_id);

            // Insert the categories into question_category table
            if (nonCodingQuestion.getQuestion_category()!= null && nonCodingQuestion.getQuestion_category().size() != 0) {
                for (String category: nonCodingQuestion.getQuestion_category()) {
                    jdbcTemplate.update("INSERT INTO question_category (question_id, category) VALUES (?, ?)", question_id, category);
                }
            }

            return question_id;
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public void updateQuestion(Question question) throws SQLException {
        try {
            String sql = "SELECT question_id, user_id, create_date, question_difficulty, question_title, question_content FROM question WHERE question_id = ?";
            Question currentQuestion = jdbcTemplate.queryForObject(sql, questionRowMapper, question.getQuestion_id());
            Question updatedQuestion = new Question();
            updatedQuestion.setQuestion_id(currentQuestion.getQuestion_id());
            updatedQuestion.setUser_id(currentQuestion.getUser_id());
            updatedQuestion.setQuestion_difficulty(currentQuestion.getQuestion_difficulty());
            updatedQuestion.setQuestion_title(currentQuestion.getQuestion_title());
            updatedQuestion.setQuestion_content(currentQuestion.getQuestion_content());

            // If user_id, question_difficulty, question_title and question_content exists, update them accordingly
            if (question.getUser_id() != null) {
                updatedQuestion.setUser_id(question.getUser_id());
            }
            if (question.getQuestion_difficulty() != null) {
                updatedQuestion.setQuestion_difficulty(question.getQuestion_difficulty());
            }
            if (question.getQuestion_title() != null) {
                updatedQuestion.setQuestion_title(question.getQuestion_title());
            }
            if (question.getQuestion_content() != null) {
                updatedQuestion.setQuestion_content(question.getQuestion_content());
            }
            sql = "UPDATE question SET user_id = ?, question_difficulty = ?, question_title = ?, question_content = ? WHERE question_id = ?";
            jdbcTemplate.update(sql, updatedQuestion.getUser_id(), updatedQuestion.getQuestion_difficulty(), updatedQuestion.getQuestion_title(), updatedQuestion.getQuestion_content(), updatedQuestion.getQuestion_id());

            // If question_category exists, update it accordingly
            if (question.getQuestion_category() != null) {
                sql = "DELETE FROM question_category WHERE question_id = ?";
                jdbcTemplate.update(sql, question.getQuestion_id());

                sql = "INSERT INTO question_category (question_id, category) VALUES (?, ?)";
                for (String category: question.getQuestion_category()) {
                    jdbcTemplate.update(sql, question.getQuestion_id(), category);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }
    }

    public Boolean updateChallenge(Challenge challenge) {
        try {
            Question question = new Question();
            question.setQuestion_id(challenge.getQuestion_id());
            question.setUser_id(challenge.getUser_id());
            question.setQuestion_difficulty(challenge.getQuestion_difficulty());
            question.setQuestion_title(challenge.getQuestion_title());
            question.setQuestion_content(challenge.getQuestion_content());
            question.setQuestion_category(challenge.getQuestion_category());

            updateQuestion(question);

            // If hints exists, update it accordingly
            if (challenge.getHints() != null) {
                String sql =  "DELETE FROM challenge_hint WHERE question_id = ?";
                jdbcTemplate.update(sql, challenge.getQuestion_id());

                sql = "INSERT INTO challenge_hint (question_id, hint) VALUES (?, ?)";
                for (String hint: challenge.getHints()) {
                    jdbcTemplate.update(sql, challenge.getQuestion_id(), hint);
                }
            }
            // If test_cases exists, update it accordingly
            if (challenge.getTest_cases() != null) {
                String sql = "DELETE FROM test_case WHERE question_id = ?";
                jdbcTemplate.update(sql, challenge.getQuestion_id());

                sql = "INSERT INTO test_case (question_id, input, output, testcase_type) VALUES (?, ?, ?, ?)";
                for (TestCase testCase: challenge.getTest_cases()) {
                    jdbcTemplate.update(sql, challenge.getQuestion_id(), testCase.getInput(), testCase.getOutput(), testCase.getTest_case_type());
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Boolean updateNonCodingQuestion(NonCodingQuestion nonCodingQuestion) {
        try {
            Question question = new Question();
            question.setQuestion_id(nonCodingQuestion.getQuestion_id());
            question.setUser_id(nonCodingQuestion.getUser_id());
            question.setQuestion_difficulty(nonCodingQuestion.getQuestion_difficulty());
            question.setQuestion_title(nonCodingQuestion.getQuestion_title());
            question.setQuestion_content(nonCodingQuestion.getQuestion_content());
            question.setQuestion_category(nonCodingQuestion.getQuestion_category());

            updateQuestion(question);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public HashMap<Integer, List<String>> categoryMapper(String requester) {
        HashMap<Integer, List<String>> all_items_map = new HashMap<>();
        List<IntWithString> all_items_list;

        String sql = "SELECT question_id, category FROM question_category";
        // For efficiency
        if (requester.equals("challenge")) {
            sql += " NATURAL JOIN challenge";
        } else if (requester.equals("non_coding_question")) {
            sql += " NATURAL JOIN non_coding_question";
        }
        all_items_list = jdbcTemplate.query(sql, categoryMapper);

        for (IntWithString i: all_items_list) {
            if (all_items_map.containsKey(i.getInt_part())) {
                all_items_map.get(i.getInt_part()).add(i.getString_part());
            } else {
                all_items_map.put(i.getInt_part(), new ArrayList<>());
                all_items_map.get(i.getInt_part()).add(i.getString_part());
            }
        }
        return all_items_map;
    }
}
