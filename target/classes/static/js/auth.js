const API_BASE = 'http://localhost:8082/api';

document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('registerForm');
    const loginForm = document.getElementById('loginForm');

    if (registerForm) {
        registerForm.addEventListener('submit', handleRegister);
    }

    if (loginForm) {
        loginForm.addEventListener('submit', handleLogin);
    }
});

async function handleRegister(e) {
    e.preventDefault();

    const user = {
        name: document.getElementById('name').value,
        email: document.getElementById('email').value,
        phone: document.getElementById('phone').value,
        address: document.getElementById('address').value,
        role: document.getElementById('role').value,
        active: true
    };

    try {
        const response = await fetch(`${API_BASE}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });

        const result = await response.json();

        if (result.success) {
            alert('Registration successful! Please login.');
            window.location.href = 'login.html';
        } else {
            alert('Registration failed: ' + result.message);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred during registration');
    }
}

async function handleLogin(e) {
    e.preventDefault();

    const email = document.getElementById('email').value;

    try {
        const response = await fetch(`${API_BASE}/auth/login?email=${encodeURIComponent(email)}`);
        const result = await response.json();

        if (result.success) {
            localStorage.setItem('user', JSON.stringify(result.data.user));
            localStorage.setItem('isAdmin', result.data.isAdmin);

            if (result.data.isAdmin) {
                window.location.href = 'admin.html';
            } else {
                window.location.href = 'user-dashboard.html';
            }
        } else {
            alert('Login failed: ' + result.message);
        }
    } catch (error) {
        console.error('Error:', error);
        alert('An error occurred during login');
    }
}

function logout() {
    localStorage.removeItem('user');
    localStorage.removeItem('isAdmin');
    window.location.href = 'index.html';
}

function getCurrentUser() {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
}

function isAdmin() {
    return localStorage.getItem('isAdmin') === 'true';
}

function goHome() {
    const user = getCurrentUser();
    if (user) {
        if (isAdmin()) {
            window.location.href = 'admin.html';
        } else {
            window.location.href = 'user-dashboard.html';
        }
    } else {
        window.location.href = 'index.html';
    }
}
