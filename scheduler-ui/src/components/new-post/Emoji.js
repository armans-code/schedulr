import React, { useState } from 'react';

function Emoji(props) {
	return (
		<button>
			<div className='text-2xl font-semibold inline-block py-1 px-2 uppercase rounded shadow-lg bg-white hover:bg-gray-100 transition duration-200 last:mr-0 mr-1'>
				{props.emoji}
			</div>
		</button>
	);
}

export default Emoji;
