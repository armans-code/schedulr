import React, { useState } from 'react';

function Hashtag(props) {
	const [active, setActive] = useState(false);

	const handleClick = () => {
		setActive(!active);
	};

	return (
		<button onClick={handleClick}>
			{!active ? (
				<div className='text-sm font-semibold inline-block py-1 px-2 uppercase rounded text-gray-600 bg-gray-200 last:mr-0 mr-1'>
					#{props.text}
				</div>
			) : (
				<div className='text-sm font-semibold inline-block py-1 px-2 uppercase rounded text-white bg-blue-600 last:mr-0 mr-1'>
					#{props.text}
				</div>
			)}
		</button>
	);
}

export default Hashtag;
