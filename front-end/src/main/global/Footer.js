import React from "react";

const Footer = () => {
  return (
    <footer className="container">
      <div className="f-row row1">
        <div className="f-brand">
          <ul>
            <li>
              <img src="imgs/logo-white.png" />
            </li>
            <li>
              <p>
                Delicious Cakes backery store offers a diversed set of cakes and
                bread
              </p>
            </li>
          </ul>
        </div>
        <div className="f-col">
          <h3>Comapany</h3>
          <ul>
            <li>
              <a href="#">About us</a>
            </li>
            <li>
              <a href="#">Tearms and Conditions</a>
            </li>
            <li>
              <a href="#">Privacy Policy</a>
            </li>
            <li>
              <a href="#">Delivery & Pickup Policy</a>
            </li>
            <li>
              <a href="#">Refund policy</a>
            </li>
            <li>
              <a href="#">Privacy Policy</a>
            </li>
          </ul>
        </div>

        <div className="f-col">
          <h3>Quik links</h3>
          <ul>
            <li>
              <a href="#">Blogs</a>
            </li>
            <li>
              <a href="#">Investements</a>
            </li>
            <li>
              <a href="#">Our Branches</a>
            </li>
          </ul>
          <br />
          <h3>Contact</h3>
          <ul>
            <li>
              <p>+ 123 000 000 000</p>
            </li>
            <li>
              <p>Mohamed Belouizdad, Algiers, Algeria |</p>
            </li>
            <li>
              <p> contact@mycake.com</p>
            </li>
          </ul>
        </div>
        <div className="f-col news-letter-col">
          <h3>news letter</h3>
          <input
            className="main-input news-letter"
            type="email"
            placeholder="Your email"
          />
          <button className="main-button new-letter-subscribe-button">
            Subscribe
          </button>
        </div>
      </div>
      <hr />
      <div className="f-row copyright">
        <p>© 2024, MyCake webapp Website Designed Kossei Necira </p>
      </div>
    </footer>
  );
};

export default Footer;
