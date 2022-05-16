import Login from "../pages/auth/Login.vue"
import Register from "../pages/auth/Register.vue"
import ChallengeFilter from "../components/challenge/ChallengeFilter.vue"
import CreateChallengePage from "../pages/challenge/CreateChallengePage.vue"
import CreateContestPage from "../pages/contest/CreateContestPage.vue"
import CreateInterviewPage from "../pages/interview/CreateInterviewPage.vue"
import Error404 from "../pages/Error404"

const routes = [
  {
    path: '/register',
    component: Register
  },
  {
    path: '/',
    component: Login
  },
  {
    path: '/filter',
    component: ChallengeFilter
  },
  {
    path: '/createQuestion',
    component: CreateChallengePage
  },
  {
    path: '/createContest',
    component: CreateContestPage
  },
  {
    path: '/createInterview',
    component: CreateInterviewPage
  },
  
  {
    path: '/:catchAll(.)',
    name: 'Error404',
    component: Error404
  }
]

export default routes