import Vue from 'vue'
import VueRouter from 'vue-router'
import {Trans} from '@/plugins/Translation'

function load(component) {
    // '@' is aliased to src/components
    return () => import(`@/views/${component}.vue`)
}

Vue.use(VueRouter)

const routes = [{
    path: '/:locale',
    component: {
        template: "<router-view></router-view>"
    },
    beforeEnter: Trans.routeMiddleware,
    children: [
        {
            path: '',
            name: 'Login',
            component: load('Login')
        },
        {
            path: 'TaskList',
            name: 'TaskList',
            component: load('TaskList')
        },
        {
            path: 'login',
            name: 'Login',
            component: load('Login')
        },
        {
            path: 'create',
            name: 'Create',
            component: load('Create')
        },
        {
            path: 'profile',
            name: 'Profile',
            component: load('Profile')
        },
        {
            path: '*',
            name: 'NotFound',
            component: load('404')
        },
        {
            path: '401',
            name: 'Access Denied',
            component: load('401')
        }
    ]
},
    {
        path: '*',
        redirect() {
            return Trans.defaultLocale;
        }
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router;
