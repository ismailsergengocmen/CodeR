import Login from "../pages/auth/Login.vue";
import Register from "../pages/auth/Register.vue";

import ChallengesPage from "../pages/challenge/ChallengesPage.vue"; //jobseeker can see
import ChallengeSpecificPage from "../pages/challenge/ChallengeSpecificPage.vue"; //JS
import CreateChallengePage from "../pages/challenge/CreateChallengePage.vue"; //Editor

import CompanySponsorContestPage from "../pages/contest/CompanySponsorContestPage.vue"; //Company
import ContestLeaderboardPage from "../pages/contest/ContestLeaderboardPage.vue"; //JS
import CreateContestPage from "../pages/contest/CreateContestPage.vue"; //Editor
import JobSeekerInContestPage from "../pages/contest/JobSeekerInContestPage.vue"; //JS

import JobSeekerContestScreen from "../pages/contests/JobSeekerContestScreen.vue"; //JS
import JobSeekerMyContestScreen from "../pages/contests/JobSeekerMyContestScreen.vue"; //JS

import CompanyInterviewPage from "../pages/interview/CompanyInterviewPage.vue"; //Company
import CompanyInterviewResult from "../pages/interview/CompanyInterviewResult.vue"; //company
import CreateInterviewPage from "../pages/interview/CreateInterviewPage.vue"; //company
import JobSeekerInterviewPage from "../pages/interview/JobSeekerInterviewPage.vue"; //JS


import Error404 from "../pages/Error404";
import LeftMenuLayout from "../layouts/LeftMenuLayout.vue";
import LeftMenu from "../layouts/LeftMenuLayout.vue";

const routes = [
  {
    path: "/register",
    component: Register,
  },
  {
    path: "/",
    component: Login,
  },
  {
    path: "/menu",
    component: LeftMenu,
  },
  {
    path: '/~',
    component: LeftMenuLayout,
    children: [
      {
        path: 'contest',
        name: 'JobSeekerContestScreen',
        component: JobSeekerContestScreen
      },
      {
        path: 'mycontest',
        name: 'JobSeekerMyContestScreen',
        component: JobSeekerMyContestScreen
      },
      {
        path: 'leaderboard',
        name: 'ContestLeaderboardPage',
        component: ContestLeaderboardPage
      },
      {
        path: 'jsInterviewPage',
        name: 'JobSeekerInterviewPage',
        component: JobSeekerInterviewPage
      },
      {
        path: "contest/:id",
        name: "JobSeekerInContestPage",
        component: JobSeekerInContestPage,
        props: true,
      },
      {
        path: "challenge/:question_id",
        name: "ChallengeSpecificPage",
        component: ChallengeSpecificPage,
        props: true,
      },
      {
        path: "challenges",
        component: ChallengesPage,
      },
      {
        path: "createQuestion",
        component: CreateChallengePage,
      },
      {
        path: "createContest",
        component: CreateContestPage,
      },
      {
        path: "createInterview",
        component: CreateInterviewPage,
      },
      {
        path: "cInterviews",
        component: CompanyInterviewPage,
      },
      {
        path: "cInterviewResults",
        component: CompanyInterviewResult,
      },
      {
        path: "cSponsorContest",
        component: CompanySponsorContestPage,
      }
    ]
  },
  //   children: [
  //     {
  //       path: 'home',
  //       name: 'Home',
  //       component: HomePage,
  //       meta: {
  //         haveAccess: ['JobSeeker', 'Company', 'Editor'] 
  //       }
  //     }
  //   ]
  // },
  // {
  //   path: '/~',
  //   component: BaseLayoutTab,
  //   children: [
  //     {
  //       path: 'challenges',
  //       name: 'Challenges',
  //       component: ChallengesPage,
  //       meta: {
  //         haveAccess: ['JobSeeker']
  //       }
  //     },
  //     {
  //       path: 'challenges/:id',
  //       name: 'ChallengeSpecificPage',
  //       component: CourseSpecificPage,
  //       props: true,
  //       meta: {
  //         haveAccess: ['JobSeeker']
  //       }
  //     },
  //     {
  //       path: 'contests',
  //       name: 'Contests',
  //       component: ContestsPage,
  //       meta: {
  //         haveAccess: ['JobSeeker']
  //       }
  //     },
  //     {
  //       path: 'contests/:id',
  //       name: 'ContestSpecificPage',
  //       component: ContestSpecificPage,
  //       props: true,
  //       meta: {
  //         haveAccess: ['JobSeeker']
  //       }
  //     }
  //   ]
  // },
  // {
  //   path: '/auth',
  //   component: BaseIndex,
  //   children: [
  //     {
  //       path: 'login',
  //       name: 'Login',
  //       component: Login
  //     },
  //     {
  //       path: 'register',
  //       name: 'Register',
  //       component: Register
  //     }
  //   ]
  // },
  {
    path: "/:catchAll(.)",
    name: "Error404",
    component: Error404,
  },
];

export default routes;
