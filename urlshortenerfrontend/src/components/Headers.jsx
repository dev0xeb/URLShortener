import React from "react";

const Header = () => (
    <header className="header">
        <div className="logo">
            Clinton URL Shortener
        </div>
        <nav>
            <ul>
                <li><a href="#features">Features</a></li>
                <li><a href="#Pricing">Pricing</a></li>
                <li><a href="#Login">Login</a></li>
            </ul>
        </nav>
        <button className="get-started">Get Started</button>    </header>
);

export default Header;