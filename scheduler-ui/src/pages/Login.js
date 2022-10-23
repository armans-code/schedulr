import { Formik } from 'formik';
import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../auth/AuthContext';

function Login() {
    const { login } = useAuth();
    const navigate = useNavigate();

    const [error, setError] = useState();

    const initialValues = {
        email: '',
        password: '',
    };

    return (
        <div className="h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-md w-full space-y-8">
                <div>
                    <h1 className="text-blue-500 text-6xl font-poppins font-extrabold text-center">
                        Scheduler
                    </h1>
                    <h2 className="mt-6 text-center text-3xl tracking-tight font-bold text-gray-900">
                        Sign in to your account
                    </h2>
                </div>
                <div className="flex justify-center self-center">
                    <div className="px-11 pt-11 pb-8 bg-white mx-auto rounded-2xl w-100 border">
                        <Formik
                            initialValues={initialValues}
                            onSubmit={async (values) => {
                                try {
                                    await login(values.email, values.password);
                                    navigate('/dashboard');
                                } catch (error) {
                                    console.log(error.code);
                                    if (error.code === 'auth/user-not-found') {
                                        setError('User not found.');
                                    } else if (
                                        error.code === 'auth/wrong-password'
                                    ) {
                                        setError('Wrong password given.');
                                    }
                                }
                            }}
                        >
                            {(formik) => {
                                const {
                                    values,
                                    handleChange,
                                    handleSubmit,
                                    handleBlur,
                                } = formik;
                                return (
                                    <form
                                        className="space-y-5"
                                        onSubmit={handleSubmit}
                                    >
                                        {error && (
                                            <span className="text-red-500 text-xs italic">
                                                {error}
                                            </span>
                                        )}
                                        <div className="space-y-2">
                                            <label className="text-sm font-medium text-gray-700 tracking-wide">
                                                Email
                                            </label>
                                            <input
                                                className=" w-full text-base px-4 py-2 border  border-gray-300 rounded-lg focus:outline-none focus:border-blue-400"
                                                id="email"
                                                value={values.email}
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                                placeholder="john@gmail.com"
                                            />
                                        </div>
                                        <div className="space-y-2">
                                            <label className="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                                                Password
                                            </label>
                                            <input
                                                className="w-full content-center text-base px-4 py-2 border  border-gray-300 rounded-lg focus:outline-none focus:border-blue-400"
                                                id="password"
                                                type="password"
                                                value={values.password}
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                                placeholder="Enter your password"
                                            />
                                        </div>
                                        <div className="flex items-center justify-between">
                                            <div className="flex items-center">
                                                <input
                                                    id="remember_me"
                                                    name="remember_me"
                                                    type="checkbox"
                                                    className="h-4 w-4 bg-blue-500 focus:ring-blue-400 border-gray-300 rounded"
                                                />
                                                <label
                                                    htmlFor="remember_me"
                                                    className="ml-2 block text-sm text-gray-800"
                                                >
                                                    Remember me
                                                </label>
                                            </div>
                                        </div>
                                        <div>
                                            <button
                                                type="submit"
                                                className="w-full flex justify-center bg-blue-600 hover:bg-blue-800 text-gray-100 p-3 rounded-full tracking-wide font-semibold shadow-lg cursor-pointer transition ease-in duration-200"
                                            >
                                                Sign in
                                            </button>
                                        </div>
                                    </form>
                                );
                            }}
                        </Formik>
                        <p className="mt-6 text-center text-sm text-gray-600">
                            No account?{' '}
                            <Link to="/register">
                                <a
                                    href="#"
                                    className="font-medium text-blue-600 hover:text-blue-500"
                                >
                                    Register here.
                                </a>
                            </Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;
