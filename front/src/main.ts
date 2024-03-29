import './assets/main.css';

import {createApp} from 'vue';
import {createPinia} from 'pinia';

import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';

import 'normalize.css';

import "bootstrap/dist/css/bootstrap-utilities.css";

// @ts-ignore
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

app.mount('#app')
