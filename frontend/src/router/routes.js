import Login from "../pages/auth/Login.vue";
import Register from "../pages/auth/Register.vue";
import ChallengeFilter from "../components/challenge/ChallengeFilter.vue";
import CreateChallengePage from "../pages/challenge/CreateChallengePage.vue";
import CreateContestPage from "../pages/contest/CreateContestPage.vue";
import CreateInterviewPage from "../pages/interview/CreateInterviewPage.vue";
import JobSeekerInContestPage from "../pages/contest/JobSeekerInContestPage.vue";
import ChallengeSpecificPage from "../pages/challenge/ChallengeSpecificPage.vue";
import Error404 from "../pages/Error404";
import JobSeekerContestScreen from "../pages/contests/JobSeekerContestScreen.vue"
import LeftMenuLayout from "../layouts/LeftMenuLayout.vue"

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
    path: "/filter",
    component: ChallengeFilter,
  },
  {
    path: "/createQuestion",
    component: CreateChallengePage,
  },
  {
    path: "/createContest",
    component: CreateContestPage,
  },
  {
    path: "/createInterview",
    component: CreateInterviewPage,
  },
  {
    path: '/~',
    component: LeftMenuLayout,
    children: [
      {
        path: 'contest',
        name: 'JobSeekerContestScreen2',
        component: JobSeekerContestScreen
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
    path: "/contest/:id",
    name: "JobSeekerInContestPage",
    component: JobSeekerInContestPage,
    props: true,
  },
  {
    path: "/challenge/:question_id",
    name: "ChallengeSpecificPage",
    component: ChallengeSpecificPage,
    props: true,
  },

  {
    path: "/:catchAll(.)",
    name: "Error404",
    component: Error404,
  },
];

export default routes;
