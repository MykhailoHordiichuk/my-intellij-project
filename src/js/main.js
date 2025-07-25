const form = document.getElementById('studentForm');
const responseEl = document.getElementById('response');

// 💡 Укажи здесь нужные адреса API (можно один или оба)
const API_SERVER_1 = 'https://easyen-front-end.onrender.com';
const API_SERVER_2 = 'https://easyeng-ccwf.onrender.com';

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

  // ⬇️ Собираем нужные запросы
  const requests = [];

  if (API_SERVER_1) {
    requests.push(fetch(`${API_SERVER_1}/api/contact`, requestOptions));
  }

  if (API_SERVER_2) {
    requests.push(fetch(`${API_SERVER_2}/api/contact`, requestOptions));
  }

  Promise.all(requests)
    .then(async (responses) => {
      const failed = responses.filter(res => !res.ok);
      if (failed.length > 0) {
        const texts = await Promise.all(failed.map(r => r.text()));
        throw new Error('One or more servers returned an error: ' + texts.join('; '));
      }

      responseEl.textContent = 'Your message was sent successfully!';
      responseEl.className = 'response-msg success';
      form.reset();

      setTimeout(() => {
        responseEl.textContent = '';
        responseEl.className = 'response-msg';
      }, 5000);
    })
    .catch(err => {
      console.error(err);
      responseEl.textContent = 'Error: ' + err.message;
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
  const req1 = fetch('https://easyen-front-end.onrender.com/api/users/login', requestOptions);
  const req2 = fetch('https://easyeng-ccwf.onrender.com/api/users/login', requestOptions);

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
    firstName: formData.get('firstname'),
    lastName: formData.get('lastname'),
    email: formData.get('email'),
    password: formData.get('password'),
    age: formData.get('age'),
    phoneNumber: formData.get('phone')
  };

  const requestOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic ' + btoa('michael:qwerty')
    },
    body: JSON.stringify(data)
  };

  const req1 = fetch('https://easyen-front-end.onrender.com/api/users/register', requestOptions);
  const req2 = fetch('https://easyeng-ccwf.onrender.com/api/users/register', requestOptions);

  Promise.all([req1, req2])
    .then(async ([res1, res2]) => {
      const json1 = await res1.json().catch(() => ({}));
      const json2 = await res2.json().catch(() => ({}));

      if (!res1.ok || !res2.ok) {
        throw new Error(
          `Registration failed:\n` +
          `Server 1: ${res1.status} - ${JSON.stringify(json1)}\n` +
          `Server 2: ${res2.status} - ${JSON.stringify(json2)}`
        );
      }

      const successMessage = json1.message || json2.message || 'Registration successful!';

      restul_create_user.textContent = successMessage;
      restul_create_user.className = 'response-msg success';
      create_account_form.reset();

      // Через 5 сек скрываем сообщение, через 10 сек скрываем форму
      setTimeout(() => {
        restul_create_user.textContent = '';
        restul_create_user.className = 'response-msg';
      }, 5000);

      setTimeout(() => {
        create_account_form.style.display = 'none';
      }, 10000);
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