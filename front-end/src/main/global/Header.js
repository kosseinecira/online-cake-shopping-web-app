import React, { useState, useEffect } from "react";
const Header = () => {
  var MAX_SMALL_SCREEN_WIDTH = 991.98;

  useEffect(() => {
    const handleResize = () => {
      const mainUlcontainer = document.querySelector(".main-ul-container");
      // Get the computed style of the element
      const mainUlContainerComputedStyle =
        window.getComputedStyle(mainUlcontainer);
      // Get the value of the "display" property
      var mainUlContainerDisplayProperty =
        mainUlContainerComputedStyle.getPropertyValue("display");
      console.log(window.innerWidth);
      if (window.innerWidth > MAX_SMALL_SCREEN_WIDTH) {
        if (mainUlContainerDisplayProperty === "none") {
          mainUlcontainer.style.display = "flex";
        }
      }
    };

    window.addEventListener("resize", handleResize);

    // Cleanup: Remove event listener on component unmount
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  const openMainNav = () => {
    const mainUlcontainer = document.querySelector(".main-ul-container");
    // Get the computed style of the element
    const mainUlContainerComputedStyle =
      window.getComputedStyle(mainUlcontainer);
    // Get the value of the "display" property
    var mainUlContainerDisplayProperty =
      mainUlContainerComputedStyle.getPropertyValue("display");
    console.log(window.innerWidth);
    if (window.innerWidth < MAX_SMALL_SCREEN_WIDTH) {
      if (mainUlContainerDisplayProperty === "none") {
        mainUlcontainer.style.display = "flex";
      } else {
        mainUlcontainer.style.display = "none";
      }
    }
  };

  return (
    /* <!-- Header start --> */
    <header>
      <div className="full-width">
        {/* <!--top bar starts --> */}
        <nav className="top-bar container">
          <div className="social-media-section">
            <a href="#" target="_blank">
              <i className="fa-brands fa-facebook-f"></i>
            </a>
            <a href="#" target="_blank">
              <i className="fa-brands fa-twitter"></i>
            </a>
            <a href="#" target="_blank">
              <i className="fa-brands fa-instagram"></i>
            </a>
            <a href="#" target="_blank">
              <i className="fa-brands fa-tiktok"></i>
            </a>
          </div>
          <div className="address-section">
            <a href="https://maps.google.com" target="_blank">
              <p>Mohamed Belouizdad, Algiers, Algeria |</p>
            </a>
          </div>
          <div className="phone-section">
            <p>+213 000-000-000</p>
          </div>
        </nav>
        {/* <!-- top bar ends --> */}
      </div>

      {/* <!-- logo container starts --> */}

      <div className="logo-container container">
        <img src="imgs/color-logo.png" />
      </div>
      {/*  <!-- logo container ends --> */}

      {/*  <!--main-nav-container--> */}
      <nav className="main-nav-container container">
        <div className="sections-section">
          <i
            className="fa-solid fa-bars toggle-menu"
            onClick={openMainNav}
            onResize={openMainNav}
          ></i>
          <ul className="main-ul-container">
            <li>
              <a href="#">Home</a>
            </li>
            <li>
              <a href="#">Chocolate</a>
            </li>
            <li>
              <a href="#">Mint</a>
            </li>
            <li>
              <a href="#">Strawberry</a>
            </li>
            <li>
              <a href="#">Birthdays</a>
            </li>
            <li>
              <a href="#">Weedings</a>
            </li>
            {/* <!--<li className="sub-ul-container"><a>Our branches</a>
                      <ul className="sub-ul">
                          <li><a href="#">Ouargla</a></li>
                          <li><a href="#">Constantine</a></li>
                      </ul>
                  </li>--> */}
          </ul>
        </div>
        <div className="authentication-section">
          <button className="main-button basket-shopping-button">
            <i className="fa fa-basket-shopping">
              <span>25</span>
            </i>
          </button>
          <button className="main-button sign-up button">
            <a href="signup.html">Sign Up</a>
          </button>
          <button className="main-button login-button">
            <a href="login.html">Login</a>
          </button>
        </div>
      </nav>
    </header>
    /* <!-- Header end --> */
  );
};

export default Header;
