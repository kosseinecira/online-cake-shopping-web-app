import React from "react";

const CakeGallery = () => {
  return (
    <section className="container">
      <div
        className="
      cakes-gallery"
      >
        <div className="filter-col">
          <div className="search-cake-row">
            <input
              className="main-input"
              type="search"
              placeholder="Torta..."
            />
            <button className="main-button">Search</button>
          </div>
          <div className="search-filter-row">
            <div className="sort-by-dropdown">
              <label for="filter1">Sort by:</label>
              <select name="price" id="filter1">
                <option value="Highest price first">Highest price first</option>
                <option value="Lowest price fitst">Lowset price first</option>
              </select>
            </div>
          </div>
          <div className="filter-by-category">
            <div className="filter-by-category-form">
              <form className="filter-form">
                <p className="filter-form-title">Category</p>
                <label for="cup-cakes">
                  CupCakes{" "}
                  <input
                    type="checkbox"
                    value="CupCakes"
                    id="cup-cakes"
                    name="cup-cakes"
                  />
                </label>
                <label for="wedding-cakes">
                  Weeding
                  <input
                    type="checkbox"
                    value="Wedding Cakes"
                    id="wedding-cakes"
                    name="cup-cakes"
                  />
                </label>
                <label for="cookies">
                  Cookies
                  <input
                    type="checkbox"
                    value="Cookies"
                    id="cookies"
                    name="cookies"
                  />
                </label>
                <label for="birthdays">
                  Birthdays
                  <input
                    type="checkbox"
                    value="Birthdays"
                    id="traditional"
                    name="traditional"
                  />
                </label>
                <label for="traditional">
                  Traditional
                  <input
                    type="checkbox"
                    value="Traditional"
                    id="traditional"
                    name="traditional"
                  />
                </label>
                <hr />
                <div className="price-filter">
                  <label>
                    Price:
                    <br />{" "}
                  </label>
                  <input className="main-input" type="text" placeholder="min" />
                  <input className="main-input" type="text" placeholder="max" />
                </div>
              </form>
            </div>
          </div>
        </div>

        {/* <!--card 1-->*/}
        <div className="cards-col">
          <div className="cake-preview-card">
            <div className="cake-preview-card-img-container">
              <img className="cake-preview-card-img" src="imgs/baklava.jpg" />
            </div>
            <div className="cake-preview-card-details">
              <p className="cake-preview-card-cake-title">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                Ratione, sunt sint doloremque unde veniam deserunt iure rem
                excepturi at architecto minus molestias iste dolor aspernatur
                distinctio perspiciatis quo similique non!
              </p>

              <div className="cake-preview-card-price">
                <p className="cake-preview-card-new-price-label">
                  <span>Starting at: </span>$ 256.94{" "}
                </p>
                <p className="cake-preview-card-old-price-label">
                  Previous was: <span>$ 299.99</span>
                </p>
              </div>
            </div>
            <div className="cake-preview-buy-button-container">
              <button className="cake-preview-buy-button main-button">
                Add to cart
              </button>
            </div>
          </div>

          {/* <!--card 2-->*/}
          <div className="cake-preview-card">
            <div className="cake-preview-card-img-container">
              <img className="cake-preview-card-img" src="imgs/baklava.jpg" />
            </div>
            <div className="cake-preview-card-details">
              <p className="cake-preview-card-cake-title">
                Chocolate Cake..Chocolate Cake..Chocolate Cake..Chocolate
                Cake..Chocolate Cake..Chocolate Cake..Chocolate Cake..
              </p>

              <div className="cake-preview-card-price">
                <p className="cake-preview-card-new-price-label">
                  <span>Starting at: </span>$ 256.94{" "}
                </p>
                <p className="cake-preview-card-old-price-label">
                  Previous was: <span>$ 299.99</span>
                </p>
              </div>
            </div>
            <div className="cake-preview-buy-button-container">
              <button className="cake-preview-buy-button main-button">
                Add to cart
              </button>
            </div>
          </div>

          {/* <!--card 3-->*/}
          <div className="cake-preview-card">
            <div className="cake-preview-card-img-container">
              <img className="cake-preview-card-img" src="imgs/baklava.jpg" />
            </div>
            <div className="cake-preview-card-details">
              <p className="cake-preview-card-cake-title">
                Lorem, ipsum dolor sit amet consectetur adipisicing elit. Quam
                at fugiat maxime fugit quia porro libero iste sed deserunt vero
                illo doloribus quaerat eos ullam minima, ratione non perferendis
                maiores.
              </p>

              <div className="cake-preview-card-price">
                <p className="cake-preview-card-new-price-label">
                  <span>Starting at: </span>$ 256.94{" "}
                </p>
                <p className="cake-preview-card-old-price-label">
                  Previous was: <span>$ 299.99</span>
                </p>
              </div>
            </div>
            <div className="cake-preview-buy-button-container">
              <button className="cake-preview-buy-button main-button">
                Add to cart
              </button>
            </div>
          </div>

          {/* <!--card 4-->*/}
          <div className="cake-preview-card">
            <div className="cake-preview-card-img-container">
              <img className="cake-preview-card-img" src="imgs/baklava.jpg" />
            </div>
            <div className="cake-preview-card-details">
              <p className="cake-preview-card-cake-title">
                Chocolate Lorem, ipsum dolor sit amet consectetur adipisicing
                elit. Provident incidunt voluptate minima possimus debitis
                iusto, distinctio aliquam, eum saepe voluptatem dolore, error
                labore expedita sequi assumenda accusantium quibusdam ratione
                voluptatum.
              </p>

              <div className="cake-preview-card-price">
                <p className="cake-preview-card-new-price-label">
                  <span>Starting at: </span>$ 256.94{" "}
                </p>
                <p className="cake-preview-card-old-price-label">
                  Previous was: <span>$ 299.99</span>
                </p>
              </div>
            </div>
            <div className="cake-preview-buy-button-container">
              <button className="cake-preview-buy-button main-button">
                Add to cart
              </button>
            </div>
          </div>

          {/* <!--card 5-->*/}
          <div className="cake-preview-card">
            <div className="cake-preview-card-img-container">
              <img className="cake-preview-card-img" src="imgs/baklava.jpg" />
            </div>
            <div className="cake-preview-card-details">
              <p className="cake-preview-card-cake-title">
                Lorem ipsum dolor sit amet consectetur adipisicing elit.
                Delectus, ab explicabo tenetur voluptatem illo ducimus suscipit
                dolorum nam saepe, laudantium quia consequatur voluptas sapiente
                nihil enim error mollitia praesentium dolore?
              </p>

              <div className="cake-preview-card-price">
                <p className="cake-preview-card-new-price-label">
                  <span>Starting at: </span>$ 256.94{" "}
                </p>
                <p className="cake-preview-card-old-price-label">
                  Previous was: <span>$ 299.99</span>
                </p>
              </div>
            </div>
            <div className="cake-preview-buy-button-container">
              <button className="cake-preview-buy-button main-button">
                Add to cart
              </button>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
};

export default CakeGallery;
