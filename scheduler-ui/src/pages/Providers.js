import React from 'react';
import { Link } from 'react-router-dom';

function Providers() {
	const client_id = 'YOUR_CLIENT_ID';
	const redirect_uri = 'http://localhost:3000/integration-success';

	return (
		<div className='w-screen h-screen flex flex-col items-center justify-center'>
			<a
				href={`https://www.facebook.com/dialog/oauth?client_id=${client_id}&redirect_uri=${redirect_uri}&scope=pages_manage_posts,pages_show_list`}
				className='border text-black p-2 lg:px-4 md:mx-2 rounded hover:bg-gray-200 hover:text-gray-700 transition-colors duration-300'
			>
				Integrate Facebook
			</a>
		</div>
	);
}

export default Providers;
