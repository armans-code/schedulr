import React, { useState } from 'react';
import { NavLink } from 'react-router-dom';
import { useAuth } from '../../auth/AuthContext';
import HeaderLink from './HeaderLink';

function Header() {
    const { logout } = useAuth();
    const handleClick = async () => {
        await logout();
    };
    return (
        <nav className="bg-white py-2 md:py-4 border">
            <div className="container px-4 mx-auto md:flex md:items-center">
                <div className="flex justify-between items-center">
                    <NavLink
                        to="/dashboard"
                        className="font-bold text-blue-600 text-3xl"
                    >
                        Scheduler
                    </NavLink>
                    <button
                        className="border border-solid border-gray-600 px-3 py-1 rounded text-gray-600 opacity-50 hover:opacity-75 md:hidden"
                        id="navbar-toggle"
                    >
                        <i className="fas fa-bars"></i>
                    </button>
                </div>

                <div
                    className="hidden md:flex flex-col md:flex-row md:ml-auto mt-3 md:mt-0"
                    id="navbar-collapse"
                >
                    <HeaderLink to="/dashboard">Dashboard</HeaderLink>
                    {/* <HeaderLink to="/your-posts">Your Posts</HeaderLink> */}
                    <HeaderLink to="/integrations">Integrations</HeaderLink>
                    <button onClick={handleClick}>
                        {/* <NavLink to='/login'> */}
                        <p className="p-2 lg:mx-4 md:mx-2 text-gray-600 hover:text-indigo-600 transition-colors duration-300 cursor-pointer">
                            Sign Out
                        </p>
                        {/* </NavLink> */}
                    </button>
                </div>
            </div>
        </nav>
    );
}

export default Header;
