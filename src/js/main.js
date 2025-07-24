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

  const requestOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('michael:qwerty')
    },
    body: JSON.stringify(data)
  };

  // Два запроса
  const req1 = fetch('https://easyen-front-end.onrender.com/api/contact', requestOptions);
  const req2 = fetch('https://easyeng-ccwf.onrender.com/api/contact', requestOptions);

  Promise.all([req1, req2])
    .then(async ([res1, res2]) => {
      if (!res1.ok || !res2.ok) {
        const text1 = await res1.text();
        const text2 = await res2.text();
        throw new Error('Login failed: one of the servers returned an error.');
      }

      responseEl.textContent = 'Are you message sended';
      responseEl.className = 'response-msg success';
      form.reset();

      setTimeout(() => {
        responseEl.textContent = '';
        responseEl.className = 'response-msg';
      }, 5000);
    })
    .catch(err => {
      console.error(err);
      responseEl.textContent = 'Error ' + err.message;
      responseEl.className = 'response-msg error';

      setTimeout(() => {
        responseEl.textContent = '';
        responseEl.className = 'response-msg';
      }, 5000);
    });
});


const loginForm = document.getElementById('login');
const resultSignIn = document.getElementById('result_signIn');

loginForm.addEventListener('submit', function (e) {
  e.preventDefault();

  const formData = new FormData(loginForm);
  const data = {
    email: formData.get('email'),
    password: formData.get('password')
  };

  const requestOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('michael:qwerty')
    },
    body: JSON.stringify(data)
  };

  // Два параллельных login-запроса
  const req1 = fetch('https://easyen-front-end.onrender.com/api/auth/login', requestOptions);
  const req2 = fetch('https://easyeng-ccwf.onrender.com/api/auth/login', requestOptions);

  Promise.all([req1, req2])
    .then(async ([res1, res2]) => {
      const body1 = await res1.text();
      const body2 = await res2.text();

      if (!res1.ok || !res2.ok) {
        throw new Error('Login failed: one of the servers returned an error.');
      }

      resultSignIn.textContent = 'Success Login';
      resultSignIn.className = 'response-msg success';
      loginForm.reset();

      setTimeout(() => {
        resultSignIn.textContent = '';
        resultSignIn.className = 'response-msg';
      }, 5000);
    })
    .catch(err => {
      console.error(err);
      resultSignIn.textContent = 'Error: ' + err.message;
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



create_account_form.addEventListener('submit', function(e) {
  e.preventDefault();
  const formData = new FormData(create_account_form);

  const data = {
    firstname: formData.get('firstname'),
    lastname: formData.get('lastname'),
    email: formData.get('email'),
    password: formData.get('password'),
    age: formData.get('age'),
    phone: formData.get('phone')
  };

  const requestOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('michael:qwerty')
    },
    body: JSON.stringify(data)
  };

  // Запрос 1
  const req1 = fetch('https://easyen-front-end.onrender.com/api/sudent/login', requestOptions);

  // Запрос 2 (пример: другой адрес)
  const req2 = fetch('https://easyeng-ccwf.onrender.com/api/student/login', requestOptions);

  // Параллельное выполнение обоих запросов
  Promise.all([req1, req2])
    .then(async ([res1, res2]) => {
      const body1 = await res1.text();
      const body2 = await res2.text();

      if (!res1.ok || !res2.ok) {
           throw new Error(
        `Login failed:\n` +
        `Server 1: ${res1.status} - ${body1}\n` +
        `Server 2: ${res2.status} - ${body2}`
        );
      }

      restul_create_user.textContent = 'You are seccessfully regestrierd!';
      restul_create_user.className = 'response-msg success';
      create_account_form.reset();

      setTimeout(() => {
        restul_create_user.textContent = '';
        restul_create_user.className = 'response-msg';
      }, 5000);
    })
    .catch(err => {
      console.error(err);
      restul_create_user.textContent = 'Error: ' + err.message;
      restul_create_user.className = 'response-msg error';

      setTimeout(() => {
        restul_create_user.textContent = '';
        restul_create_user.className = 'response-msg';
      }, 5000);
    });
});