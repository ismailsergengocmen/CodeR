import Login from "../pages/auth/Login.vue";
import Register from "../pages/auth/Register.vue";
import ChallengeFilter from "../components/challenge/ChallengeFilter.vue";
import ChallengeSpecificPage from "../pages/challenge/ChallengeSpecificPage.vue";
import Error404 from "../pages/Error404";

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
    path: "/challenge/:course_id",
    component: ChallengeSpecificPage,
    props: true,
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
    path: "/:catchAll(.*)*",
    name: "Error404",
    component: Error404,
  },
];
export default routes;
