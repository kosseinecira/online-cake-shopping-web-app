import React from "react";

const Login = () => {
  return (
    <section class="login-section">
      <div class="login-form-left-part">
        <div class="login-form-left-part-content">
          <p class="login-form-left-part-welcome-heading">WELCOME BACK :)</p>
          <p class="login-form-left-part-welcome-details">
            What ever you will order, it's tasty :p
          </p>
        </div>
      </div>
      <form class="form" action="" method="GET">
        <p class="form-title">Login</p>
        <label for="email">Email:</label>
        <input class="main-input" type="email" required />
        <label for="password">Password:</label>
        <input class="main-input" type="password" required />
        <input class="main-button login-button" type="submit" value="login" />
        <a class="" href="#">
          Sign Up
        </a>
        <a class="forget-password-link" href="#">
          I forget my password +___+!{" "}
        </a>
        <div class="social-media-login">
          <button class="main-button google-login-button">Google</button>
          <button class="main-button facebook-login-button">Facebook</button>
          <button class="main-button twitter-login-button">Twitter</button>
        </div>
      </form>
    </section>
  );
};

export default Login;
