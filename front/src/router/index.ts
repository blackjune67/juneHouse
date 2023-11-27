import {createRouter, createWebHistory} from 'vue-router'
import HomeView from '../views/HomeView.vue'
import WriteView from '../views/WriteView.vue'
import ReadView from "@/views/ReadView.vue";
import EditView from "@/views/EditView.vue";
import LoginView from "@/views/LoginView.vue";
import SignupView from "@/views/SignupView.vue";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            component: HomeView
        },
        {
            path: '/signup',
            name: 'Signup',
            component: SignupView
        },
        {
            path: '/login',
            name: 'login',
            component: LoginView
        },
        {
            path: '/write',
            name: 'write',
            component: WriteView,
            // component: () => import('../views/WriteView.vue')
        },
        {
            path: '/read/:postId',
            name: 'read',
            component: ReadView,
            props: true,
        },
        {
            path: '/edit/:postId',
            name: 'edit',
            component: EditView,
            props: true,
        }
    ]
})

export default router
