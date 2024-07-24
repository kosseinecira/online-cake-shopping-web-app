import "./app.css";
import Header from "./main/global/Header";
import Footer from "./main/global/Footer";
import { Route, Routes } from "react-router-dom";
import HomePage from "./main/HomePage";
import CakePage from "./main/CakePage";
import Login from "./main/Login";
import SignUp from "./main/SignUp";
import CakeGallery from "./main/CakeGallery";
function App() {
  return (
    <div className="app">
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/cake" element={<CakePage />} />
        <Route path="/cakes" element={<CakeGallery />} />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
