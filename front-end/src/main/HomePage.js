import React from "react";
import LandingSection from "./global/LandingSection";

const HomePage = () => {
  return (
    <>
      <LandingSection />
      <main>
        {/* <!--Intorduction section to give the visitor an Idea about the website:--> */}
        {/*  <!-- brief-intro-section starts --> */}
        <section className="brief-intro-section">
          <div className="brief-intro container">
            <h2 className="brief-intro-heading">Welcome To myCake </h2>
            <h2 className="brief-intro-who-are-we">Who are we?</h2>
            <div className="brief-intro-details">
              <div className="brief-intro-card">
                <img
                  className="brief-intro-card-img"
                  src="imgs/Person Making Dough.jpg"
                />
              </div>
              <div className="brief-intro-information">
                <p className="brief-intro-description">
                  We work with the finest artisanal bakers and deliver across
                  the UK, in 24-48 hours. Whether it's a birthday cake delivery
                  for a child or an adult, a wedding cake delivery for your
                  special day or bespoke cakes designed to your order, we'd love
                  to help. We also offer an extensive range of cakes and bakes
                  for corporate gifting and events. You can contact our Cake
                  Concierge on 020 3239 4399 between 10am - 6pm, seven days a
                  week, or email orders@mybaker.co.
                </p>
                <div className="brief-intro-description-footer">
                  <p className="read-more-about-our-acheivements">
                    Read More about Our Acheivements
                  </p>
                  <butto className="main-button read-more-about-us-button">
                    Read more
                  </butto>
                </div>
              </div>
            </div>
          </div>
        </section>
        {/* <!-- brief-intro-section ends --> */}

        {/* <!-- offers section starts --> */}
        <section className="offer container">
          <div className="offer-section-img">
            <img src="imgs/offer-cake.jpg" />
          </div>
          <div className="offer-details">
            <p className="offer-details-heading">
              RAMADAN OFFER <span className="discount-span">50% OFF</span>
            </p>
            <p className="offer-details-description">
              Durining RAMADAN we make your cake with half of the original
              price.
            </p>
            <button className="main-button offer-button">ORDER NOW</button>
          </div>
        </section>
        {/* <!-- offers section ends --> */}

        {/* <!-- reviews section starts --> */}
        <div className="reviews-section-container container">
          <section className="reviews-section">
            <i className="fa-solid fa-circle-chevron-right next-review"></i>
            <i className="fa-solid fa-circle-chevron-left previous-review"></i>
            <img
              className="review-image"
              src="imgs/review-profile-img.jpg"
              alt="profile_pict"
            />
            <p className="review-person-name">Locas Palotocas</p>
            <p className="review-section-text">
              Lorem ipsum dolor sit, amet consectetur adipisicing elit. Facilis
              rem, labore eos aperiam esse accusantium illo quis libero totam
              velit autem ut natus est quasi unde, blanditiis repudiandae
              voluptates ullam.
            </p>
          </section>
        </div>
        {/* <!-- reviews section starts --> */}

        {/* <!-- our product section starts --> */}
        <section className="our-products-section">
          <div className="our-products-container container">
            <h2 id="our-product-section-heading">Our Products</h2>
            <p id="our-product-section-content">
              For more than 25 years, Magnolia Bakery has been making America’s
              favorite baked goods the old-fashioned way: from scratch, in small
              batches, and using the finest ingredients.
            </p>
            <div className="products">
              <div className="product">
                <img className="product-img" src="imgs/cherry-cake.jpg" />
                <p>Cakes</p>
              </div>
              <div className="product">
                <img className="product-img" src="imgs/Cupcake.jpg" />
                <p>Cupcakes</p>
              </div>
              <div className="product">
                <img className="product-img" src="imgs/patisserie2.jpg" />
                <a href="#" target="_blank">
                  <p>Paisserie</p>
                </a>
              </div>
              <div className="product">
                <img className="product-img" src="imgs/cookies.jpg" />
                <p>Cookies</p>
              </div>
              <div className="product">
                <img className="product-img" src="imgs/baklava.jpg" />
                <p>Traditional</p>
              </div>
              <div className="product">
                <img
                  className="product-img"
                  src="imgs/Bunch of Croissant.jpg"
                />
                <p>Croissant</p>
              </div>
              <div className="product">
                <img className="product-img" src="imgs/icecream.jpg" />
                <p>Ice Cream</p>
              </div>
              <div className="product">
                <img className="product-img" src="imgs/pineapplejuice.jpg" />
                <p>Juice</p>
              </div>
              <div className="product">
                <img className="product-img" src="imgs/bread.jpg" />
                <p>Bread</p>
              </div>
            </div>
          </div>
        </section>
        {/* <!-- our product section ends --> */}

        {/* <!-- brief-intro-container ends --> */}
      </main>
    </>
  );
};

export default HomePage;
