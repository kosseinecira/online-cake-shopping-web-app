import React from "react";

const SignUp = () => {
  return (
    <section class="signup-section">
      <div class="signup-form-left-part">
        <div class="signup-form-left-part-content">
          <p class="signup-form-left-part-welcome-heading">
            WELCOME TO OUR BACKERY
          </p>
          <p class="signup-form-left-part-welcome-details">
            You Will Not Regret By Choosing us. Our Team Will Make The BEST Cake
            You Will Ever Taste
          </p>
        </div>
      </div>
      <form class="form" action="" method="GET">
        <p class="form-title">create account</p>
        <label for="email">Email:</label>
        <input class="main-input" type="email" required />
        <label for="password">Password:</label>
        <input class="main-input" type="password" required />
        <input
          class="main-button signup-button"
          type="submit"
          value="create account"
        />
        <a class="" href="#">
          Already have an account?
        </a>
        <a class="forget-password-link" href="#">
          Need Help?
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

export default SignUp;
