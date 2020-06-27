import React from 'react';
import {Link} from "react-router-dom";

function Navbar() {
    return (
        <header className="rs-navbar">
            <Link className="navbar-brand" to={'/'}>
                RESTAURANT
            </Link>
            <div className="navbar-option dropdown">
                <button type="button" className="navbar-button" data-toggle="dropdown">
                    Duc1111
                    <i className="fas fa-chevron-down"></i>
                </button>
                <div className="dropdown-menu">
                    <Link className="dropdown-item" to={'/'}>
                        Bán hàng
                    </Link>
                    <Link className="dropdown-item" to={'/backend'}>
                        Quản lý
                    </Link>
                    <Link className="dropdown-item" to={'/'}>
                        Tài khoản
                    </Link>
                    <Link className="dropdown-item" to={'/'}>
                        Đăng xuất
                    </Link>
                </div>
            </div>
        </header>
    );
}

Navbar.propTypes = {};

export default Navbar;