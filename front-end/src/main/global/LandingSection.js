import React from "react";
const LandingSection = () => {
  return (
    /*    <!-- landing section starts -->*/
    <section className="landing-section">
      {/*        <!-- landing-section-cake-bg starts-->*/}
      <div className="landing-section-cake-bg">
        <video autoPlay muted loop id="myVideo">
          <source src="imgs/showVideo.mp4" type="video/mp4" />
        </video>
        {/*      <!--<img src="imgs/choco-cake-with-strawberry.png">-->*/}
      </div>
      {/*       <!-- landing-section-cake-bg ends-->*/}
      {/*        <!-- landing-section-details starts-->*/}

      <div className="landing-section-details container">
        {/*    <!-- landing-section-text starts-->*/}
        <div className="landing-section-text">
          <div className="landing-section-title">
            <p>
              Our Chiefs Cake you the most Delicious cake you will ever have
            </p>
          </div>
          <div className="landing-section-description">
            <p>
              Lorem ipsum dolor sit, amet consectetur adipisicing elit.
              Blanditiis aut dolorem autem nesciunt, magni iste ex expedita sunt
              adipisci libero officiis voluptates totam, ad porro quis impedit,
              illo voluptate nisi.
            </p>
          </div>
          <button className="main-button landing-section-order-button">
            Order a cake
          </button>
        </div>
        {/*       <!-- landing-section-text ends -->*/}
      </div>

      {/*      <!-- landing-section-details ends-->*/}
    </section>
  );
};

export default LandingSection;
