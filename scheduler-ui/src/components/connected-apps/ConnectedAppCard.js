import React from 'react';

function ConnectedAppCard(props) {
	const { name, desc, image, connected } = props;

	const buttonClassName = connected
		? 'bg-green-100 hover:bg-green-300 text-green-700 duration-200 rounded-lg px-2 py-1'
		: 'border bg-white hover:bg-gray-100 text-gray-600 duration-200 rounded-lg px-2 py-1';

	return (
		<div className='flex flex-col gap-2 border p-4 rounded-lg w-72 shadow'>
			<div className='w-full flex items-center justify-between'>
				<div className='w-14'>
					<img className='w-full h-full' src={image} />
				</div>
				<button flex className={buttonClassName}>
					{connected ? 'Connected' : 'Connect'}
				</button>
			</div>
			<p>{name}</p>
			<p className='text-gray-500'>{desc}</p>
		</div>
	);
}

export default ConnectedAppCard;
