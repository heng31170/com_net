import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  // 根路径
  {
    path: '/',
    redirect: '/course'
  },
  // 课程管理
  {
    path: '/course',
    name: 'course',
    component: () => import('../views/tlias/Course.vue')
  },
  // 课程详情管理
  {
    path: '/course/:courseId',
    name: 'courseDetail',
    component: () => import('../views/tlias/CourseDetail.vue') 
  },
  // 教师管理
  {
    path: '/teacher',
    name: 'teacher',
    component: () => import('../views/tlias/Teacher.vue')
  },
  // 教师详情管理
  {
    path: '/teacher/:teacher',
    name: 'teacherDetail',
    component: () => import('../views/tlias/TeacherDetail.vue') 
  },
   // 讨论管理
   {
    path: '/discussion',
    name: 'discussion',
    component: () => import('../views/tlias/Discussion.vue')
  },
  // 讨论详情管理
  {
    path: '/discussion/:discussionId',
    name: 'discussionDetail',
    component: () => import('../views/tlias/DiscussionDetail.vue') 
  },
     // 考试管理
     {
      path: '/exam',
      name: 'exam',
      component: () => import('../views/tlias/Exam.vue')
    },
    // 考试详情管理
    {
      path: '/exam/:examId',
      name: 'examDetail',
      component: () => import('../views/tlias/ExamDetail.vue') 
    },
  

  // 登录管理
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/tlias/LoginView.vue')
  },
  // 注册管理
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/tlias/RegisterView.vue')
  },
  // 个人中心管理
  {
    path: '/person/:personId',
    name: 'person',
    component: () => import('../views/tlias/PersonView.vue')
  },
    // 课程后台管理
    {
      path: '/courseAdmin',
      name: 'courseAdmin',
      component: () => import('../views/tlias/Admin_Course.vue')
    },
    // 教师后台管理
    {
      path: '/teacherAdmin',
      name: 'teacherAdmin',
      component: () => import('../views/tlias/Admin_Teacher.vue')
    },
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router