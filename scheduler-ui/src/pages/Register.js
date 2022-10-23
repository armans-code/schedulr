import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Formik } from 'formik';
import { useAuth } from '../auth/AuthContext';
import { useMutation } from '@apollo/client';
import { REGISTER_BUSINESS } from '../graphql/mutations';

function Register() {
    const { login } = useAuth();
    const navigate = useNavigate();

    const initialValues = {
        name: '',
        email: '',
        password: '',
        confirmPassword: '',
    };

    const validate = (values) => {
        let errors = {};
        if (!values.name) errors.name = 'Business name is required.';
        if (!values.email) errors.email = 'Email is required.';
        if (!values.password) errors.password = 'Password is required.';
        if (values.confirmPassword !== values.password)
            errors.confirmPassword = 'Passwords must match.';
        return errors;
    };

    const [registerBusiness, { data, loading, error }] =
        useMutation(REGISTER_BUSINESS);

    if (data) console.log(data);
    if (error) console.error(error);

    return (
        <div className="h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-md w-full space-y-8">
                <div>
                    <h1 className="text-blue-500 text-6xl font-poppins font-extrabold text-center">
                        Scheduler
                    </h1>
                    <h2 className="mt-6 text-center text-3xl tracking-tight font-bold text-gray-900">
                        Register an account
                    </h2>
                </div>
                <div className="flex justify-center self-center">
                    <div className="px-11 pt-11 pb-8 bg-white mx-auto rounded-2xl w-100 border">
                        <Formik
                            initialValues={initialValues}
                            validate={validate}
                            onSubmit={(values) => {
                                // same shape as initial values
                                registerBusiness({
                                    variables: {
                                        RegisterBusinessInput: {
                                            name: values.name,
                                            email: values.email,
                                            password: values.password,
                                        },
                                    },
                                }).then(async () => {
                                    try {
                                        await login(
                                            values.email,
                                            values.password,
                                        );
                                        navigate('/dashboard');
                                    } catch (error) {
                                        console.log(error);
                                    }
                                });
                            }}
                        >
                            {(formik) => {
                                const {
                                    values,
                                    handleChange,
                                    handleSubmit,
                                    errors,
                                    touched,
                                    handleBlur,
                                } = formik;
                                return (
                                    <form
                                        className="space-y-5"
                                        onSubmit={handleSubmit}
                                    >
                                        <div className="space-y-2">
                                            <label className="text-sm font-medium text-gray-700 tracking-wide">
                                                Business Name
                                            </label>
                                            <input
                                                className=" w-full text-base px-4 py-2 border  border-gray-300 rounded-lg focus:outline-none focus:border-blue-400"
                                                id="name"
                                                value={values.name}
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                                placeholder="My Business"
                                            />
                                            {errors.name && touched.name && (
                                                <span className="text-red-500 text-xs italic">
                                                    {errors.name}
                                                </span>
                                            )}
                                        </div>
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
                                                placeholder="app@business.com"
                                            />
                                            {errors.email && touched.email && (
                                                <span className="text-red-500 text-xs italic">
                                                    {errors.email}
                                                </span>
                                            )}
                                        </div>
                                        <div className="space-y-2">
                                            <label className="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                                                Password
                                            </label>
                                            <input
                                                className="w-full content-center text-base px-4 py-2 border  border-gray-300 rounded-lg focus:outline-none focus:border-blue-400"
                                                id="password"
                                                value={values.password}
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                                placeholder="Enter your password"
                                                type="password"
                                            />
                                            {errors.password &&
                                                touched.password && (
                                                    <span className="text-red-500 text-xs italic">
                                                        {errors.password}
                                                    </span>
                                                )}
                                        </div>
                                        <div className="space-y-2">
                                            <label className="mb-5 text-sm font-medium text-gray-700 tracking-wide">
                                                Confirm Password
                                            </label>
                                            <input
                                                className="w-full content-center text-base px-4 py-2 border  border-gray-300 rounded-lg focus:outline-none focus:border-blue-400"
                                                id="confirmPassword"
                                                type="password"
                                                value={values.confirmPassword}
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                                placeholder="Enter your password"
                                            />
                                            {errors.confirmPassword &&
                                                touched.confirmPassword && (
                                                    <span className="text-red-500 text-xs italic">
                                                        {errors.confirmPassword}
                                                    </span>
                                                )}
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
                                                Register
                                            </button>
                                        </div>
                                    </form>
                                );
                            }}
                        </Formik>
                        <p className="mt-6 text-center text-sm text-gray-600">
                            Already have an account?
                            <Link to="/login">
                                <a
                                    href="#"
                                    className="font-medium text-blue-600 hover:text-blue-500"
                                >
                                    Login here.
                                </a>
                            </Link>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Register;
