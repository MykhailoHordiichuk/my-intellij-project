  const form = document.getElementById('studentForm');
  const responseEl = document.getElementById('response');

  form.addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(form);
    const data = {
      name: formData.get('fullname'),
      email: formData.get('email'),
      message: formData.get('message')
    };

    fetch('https://easyen-front-end.onrender.com/api/contact', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa('michael:qwerty')
      },
      body: JSON.stringify(data)
    })
    .then(res => {
      if (!res.ok) throw new Error('Request failed: ' + res.status);
      return res.json();
    })
    .then(result => {
      responseEl.textContent = 'Message sent successfully!';
      responseEl.className = 'response-msg success';
      form.reset();

      setTimeout(() => {
        responseEl.textContent = '';
        responseEl.className = 'response-msg';
      }, 5000);
    })
    .catch(err => {
      console.error(err);
      responseEl.textContent = 'Error sending message: ' + err.message;
      responseEl.className = 'response-message error';

      setTimeout(() => {
        responseEl.textContent = '';
        responseEl.className = 'response-msg';
      }, 5000);
    });
  });



  // login form

const loginForm = document.getElementById('login');
const resultSignIn = document.getElementById('result_signIn');

loginForm.addEventListener('submit', function (e) {
  e.preventDefault();

  const formData = new FormData(loginForm);
  const data = {
    email: formData.get('email'),
    password: formData.get('password')
  };

  fetch('https://easyen-front-end.onrender.com/api/auth/login	', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('michael:qwerty') // если требуется basic auth
    },
    body: JSON.stringify(data)
  })
  .then(async res => {
    const responseBody = await res.text(); // читаем как текст (или res.json() если точно JSON)
    
    if (!res.ok) {
      throw new Error(responseBody || `Login failed: ${res.status}`);
    }

    resultSignIn.textContent = 'Login successful!';
    resultSignIn.className = 'response-msg success';
    loginForm.reset();

    setTimeout(() => {
      resultSignIn.textContent = '';
      resultSignIn.className = 'response-msg';
    }, 5000);
  })
  .catch(err => {
    console.error(err);
    resultSignIn.textContent = 'Ошибка: ' + err.message;
    resultSignIn.className = 'response-msg error';

    setTimeout(() => {
      resultSignIn.textContent = '';
      resultSignIn.className = 'response-msg';
    }, 5000);
  });
});






// create account form


const create_account_form = document.getElementById('create_account_form');
const restul_create_user = document.getElementById('result_create_account');



create_account_form.addEventListener('submit', function(e){

  e.preventDefault();
  const formData = new FormData(create_account_form);

  const data = {
    username: formData.get('username'),
    email: formData.get('email'),
    password: formData.get('password'),
    fullName: formData.get('fullname')
  };

 fetch('https://easyen-front-end.onrender.com/api/auth/register	', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('michael:qwerty') // если требуется basic auth
    },
    body: JSON.stringify(data)
  })
  .then(async res => {
    const responseBody = await res.text(); // читаем как текст (или res.json() если точно JSON)
    
    if (!res.ok) {
      throw new Error(responseBody || `Login failed: ${res.status}`);
    }

    restul_create_user.textContent = 'Login successful!';
    restul_create_user.className = 'response-msg success';
    create_account_form.reset();

    setTimeout(() => {
      restul_create_user.textContent = '';
      restul_create_user.className = 'response-msg';
    }, 5000);
  })
  .catch(err => {
    console.error(err);
    restul_create_user.textContent = 'Ошибка: ' + err.message;
    restul_create_user.className = 'response-msg error';

    setTimeout(() => {
      restul_create_user.textContent = '';
      restul_create_user.className = 'response-msg';
    }, 5000);
  });
});