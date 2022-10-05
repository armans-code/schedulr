import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

function Register() {
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');
	const [error, setError] = useState('');

	const navigate = useNavigate();

	const handleLogin = () => {
		console.log('a');
	};

	return (
		<div className='h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8'>
			<div className='max-w-md w-full space-y-8'>
				<div>
					<h1 className='text-blue-500 text-6xl font-poppins font-extrabold text-center'>
						Scheduler
					</h1>
					<h2 className='mt-6 text-center text-3xl tracking-tight font-bold text-gray-900'>
						Register an account
					</h2>
				</div>
				<div class='flex justify-center self-center'>
					<div class='px-11 pt-11 pb-8 bg-white mx-auto rounded-2xl w-100 border'>
						<div class='space-y-5'>
							<div class='space-y-2'>
								<label class='text-sm font-medium text-gray-700 tracking-wide'>
									Business Name
								</label>
								<input
									class=' w-full text-base px-4 py-2 border  border-gray-300 rounded-lg focus:outline-none focus:border-blue-400'
									type=''
									placeholder='My Business'
								/>
							</div>
							<div class='space-y-2'>
								<label class='text-sm font-medium text-gray-700 tracking-wide'>
									Email
								</label>
								<input
									class=' w-full text-base px-4 py-2 border  border-gray-300 rounded-lg focus:outline-none focus:border-blue-400'
									type=''
									placeholder='app@business.com'
								/>
							</div>
							<div class='space-y-2'>
								<label class='mb-5 text-sm font-medium text-gray-700 tracking-wide'>
									Password
								</label>
								<input
									class='w-full content-center text-base px-4 py-2 border  border-gray-300 rounded-lg focus:outline-none focus:border-blue-400'
									type=''
									placeholder='Enter your password'
								/>
							</div>
							<div class='space-y-2'>
								<label class='mb-5 text-sm font-medium text-gray-700 tracking-wide'>
									Confirm Password
								</label>
								<input
									class='w-full content-center text-base px-4 py-2 border  border-gray-300 rounded-lg focus:outline-none focus:border-blue-400'
									type=''
									placeholder='Enter your password'
								/>
							</div>
							<div class='flex items-center justify-between'>
								<div class='flex items-center'>
									<input
										id='remember_me'
										name='remember_me'
										type='checkbox'
										class='h-4 w-4 bg-blue-500 focus:ring-blue-400 border-gray-300 rounded'
									/>
									<label
										for='remember_me'
										class='ml-2 block text-sm text-gray-800'
									>
										Remember me
									</label>
								</div>
								{/* <div class='text-sm'>
									<a href='#' class='text-blue-400 hover:text-blue-500'>
										Forgot your password?
									</a>
								</div> */}
							</div>
							<div>
								<button
									type='submit'
									class='w-full flex justify-center bg-blue-600 hover:bg-blue-800 text-gray-100 p-3 rounded-full tracking-wide font-semibold shadow-lg cursor-pointer transition ease-in duration-200'
								>
									Register
								</button>
							</div>
						</div>
						<p className='mt-6 text-center text-sm text-gray-600'>
							Already have an account?{' '}
							<Link to='/login'>
								<a
									href='#'
									className='font-medium text-blue-600 hover:text-blue-500'
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
