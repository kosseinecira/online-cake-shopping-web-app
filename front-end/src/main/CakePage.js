import React from "react";

const CakePage = () => {
  return (
    <section className="cake-show">
      <div className="path">
        <ul>
          <li>
            <a href="#">Home</a>
          </li>
          <li>
            <a href="#">Category1</a>
          </li>
          <li>
            <a href="#">SubCategory</a>
          </li>
        </ul>
      </div>
      <div className="cake-details">
        <div className="img">
          <img className="cake-img" src="imgs/lemon-cake.jpg" />
        </div>
        <div className="cake-information">
          <div className="cake-name">
            <h3>Cake Name::</h3>
          </div>
          <div className="cake-price">
            <p className="old-price">213.26$</p>
            <p className="price-off">| 40% OFF</p>
            <p className="new-price">152.33$</p>
          </div>
          <div className="cake-description">
            <div className="general-cake-description">
              <h4>Cake description:</h4>
              <p>
                Lorem ipsum dolor sit amet consectetur, adipisicing elit.
                Accusantium nisi eveniet incidunt sint. Maiores obcaecati
                molestiae non consequatur, hic id! Repudiandae quas facere
                officia blanditiis numquam a aperiam maxime nulla?
              </p>
            </div>
            <div className="cake-allergic-description">
              <h4>Ingredient that may cause Allergic Consequences:</h4>
              <p>
                Allergic Allergic Lorem ipsum dolor sit amet consectetur
                adipisicing elit. Quaerat maiores, odit placeat eligendi minima
                exercitationem aliquid corrupti fugiat repellat nisi, vel
                sapiente odio dolorem voluptas nesciunt iste incidunt molestias
                ab.
              </p>
            </div>

            <div className="cake-specific-details">
              <h4>Details</h4>
              <p>
                Flavor: <span> Chocolate</span>
              </p>
              <p>
                Weight: <span>500</span> g
              </p>
              <p>
                Height: <span>15</span> cm
              </p>
              <p>
                Diameter: <span>25</span> cm
              </p>
              <p>
                Net Quantity: <span>in Order</span>
              </p>
              <h4>Ingredients:</h4>
              <ul>
                <li>
                  <p>Sugar,</p>
                </li>
                <li>
                  <p>Eggs,</p>
                </li>
                <li>
                  <p>Butter,</p>
                </li>
                <li>
                  <p>Baking Powder,</p>
                </li>
              </ul>
              <div className="delivery-information">
                <h4>Delivery Information: </h4>
                <p>
                  Company: <span>DHL</span>
                </p>
                <p>
                  Duration: <span>10 days</span>
                </p>
              </div>
              <div className="message-on-cake">
                <label>Message</label>
                <input
                  type="text"
                  className="main-input"
                  placeholder="Happy Birthday"
                />
              </div>
            </div>
          </div>
          <div className="order">
            <div className="quantity">
              <input
                className="main-input"
                type="text"
                id="quantity"
                name="quantity"
                placeholder="Quantity 0"
              />
            </div>
            <button className="main-button order-button">Add To Cart</button>
          </div>
          <div className="share-social-media">
            <p>Share with your friends:</p>
            <ul>
              <li>
                <a href="#" target="_blank">
                  <i className="fa-brands fa-facebook-f"></i>
                </a>
              </li>
              <li>
                <a href="#" target="_blank">
                  <i className="fa-brands fa-twitter"></i>
                </a>
              </li>
              <li>
                <a href="#" target="_blank">
                  <i className="fa-brands fa-instagram"></i>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </section>
  );
};

export default CakePage;
