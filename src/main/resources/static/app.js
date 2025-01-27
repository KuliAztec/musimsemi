const API_URL = 'http://localhost:8080/api/todos'; // Update with your backend URL

let currentUser = null;
let jwtToken = null;

// Load tasks when page loads
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('taskInput').addEventListener('keypress', function (e) {
        if (e.key === 'Enter') {
            addTask();
        }
    });
});

let tasks = [];

async function login() {
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    const response = await fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    });
    
    if (response.ok) {
        const data = await response.json();
        jwtToken = data.token;
        currentUser = username;
        document.getElementById('loginForm').style.display = 'none';
        document.getElementById('todoApp').style.display = 'block';
        fetchTasks();
    }
}

async function register() {
    const username = document.getElementById('regUsername').value;
    const password = document.getElementById('regPassword').value;
    
    const response = await fetch('/api/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ username, password })
    });
    
    if (response.ok) {
        alert('Registration successful! Please login.');
        showLogin();
    }
}

function logout() {
    jwtToken = null;
    currentUser = null;
    document.getElementById('loginForm').style.display = 'block';
    document.getElementById('todoApp').style.display = 'none';
}

async function fetchTasks() {
    try {
        const response = await fetch(API_URL, {
            headers: {
                'Authorization': `Bearer ${jwtToken}`
            }
        });
        const fetchedTasks = await response.json();
        tasks = fetchedTasks;
        renderTasks(tasks);
    } catch (error) {
        console.error('Error fetching tasks:', error);
    }
}

function renderTasks(tasks) {
    const taskList = document.getElementById('taskList');
    taskList.innerHTML = '';

    tasks.forEach(task => {
        renderTask(task);
    });
}

function renderTask(task) {
    const taskList = document.getElementById('taskList');
    const taskItem = document.createElement('li');
    taskItem.setAttribute('data-id', task.id);
    taskItem.innerHTML = `
        <input type="checkbox" ${task.completed ? 'checked' : ''} onclick="toggleTask(${task.id})">
        <span class="${task.completed ? 'completed' : ''}">${task.title}</span>
        <button onclick="startEdit(${task.id})" class="edit-btn">Edit</button>
        <button onclick="deleteTask(${task.id})" class="delete-btn">Delete</button>
    `;
    taskList.appendChild(taskItem);
}

function addTask() {
    const taskInput = document.getElementById('taskInput');
    const taskTitle = taskInput.value.trim();
    if (taskTitle === '') return;

    const task = {
        id: Date.now(),
        title: taskTitle,
        completed: false
    };

    tasks.push(task);
    renderTask(task);
    taskInput.value = '';
}

function toggleTask(taskId) {
    const task = tasks.find(t => t.id === taskId);
    if (task) {
        task.completed = !task.completed;
        updateTask(task);
    }
}

function updateTask(updatedTask) {
    const taskItem = document.querySelector(`li[data-id="${updatedTask.id}"]`);
    if (taskItem) {
        const checkbox = taskItem.querySelector('input[type="checkbox"]');
        const span = taskItem.querySelector('span');
        checkbox.checked = updatedTask.completed;
        span.className = updatedTask.completed ? 'completed' : '';
        span.textContent = updatedTask.title;
    }
}

function startEdit(taskId) {
    const task = tasks.find(t => t.id === taskId);
    if (task) {
        const taskInput = document.getElementById('taskInput');
        taskInput.value = task.title;
        // Add additional logic to handle the editing process
    }
}

function deleteTask(taskId) {
    tasks = tasks.filter(t => t.id !== taskId);
    const taskItem = document.querySelector(`li[data-id="${taskId}"]`);
    if (taskItem) {
        taskItem.remove();
    }
}