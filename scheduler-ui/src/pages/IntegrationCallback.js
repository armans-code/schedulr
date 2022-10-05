import React from 'react';
import { useSearchParams } from 'react-router-dom';

function IntegrationCallback() {
	const [searchParams, setSearchParams] = useSearchParams();

//	todo: fix

	if (searchParams.get('error'))
		return (
			<div className='w-full h-screen flex flex-col items-center justify-center'>
				<h1 className='text-red-500 text-6xl font-poppins font-extrabold text-center'>
					Error!
				</h1>
				<p>
					<b>Error:</b> {searchParams.get('error')}
				</p>
				<p>
					<b>Error code:</b> {searchParams.get('error_code')}{' '}
				</p>
				<p>
					<b>Error description:</b> {searchParams.get('error_description')}{' '}
				</p>
			</div>
		);
	else {
		return (
			<div className='w-full h-screen flex flex-col items-center justify-center'>
				<h1 className='text-indigo-600 text-6xl font-poppins font-extrabold text-center'>
					Success!
				</h1>
				<p>You've successfully linked your Facebook account to Schedulify!</p>
			</div>
		);
	}
}

export default IntegrationCallback;
