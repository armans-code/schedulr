import React from 'react';
import ConnectedAppCard from '../components/connected-apps/ConnectedAppCard';

function ConnectedApps() {
	return (
		<div className='flex flex-col px-24'>
			<div className='flex flex-col gap-3 mt-8'>
				<h1 className='text-3xl font-medium'>Connected apps</h1>
				{/* <div class='flex-grow border-t border-gray-200' /> */}
				<p className='text-gray-500'>
					Integrate your favorite apps in order to boost your efficiency with
					scheduled posts and optimal hashtags!
				</p>
			</div>
			<div className='flex gap-9 mt-8'>
				<ConnectedAppCard
					name='Twitter'
					connected={true}
					desc='Find optimal hashtags and schedule posts for Twitter.'
					image='https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Twitter-logo.svg/440px-Twitter-logo.svg.png'
				/>
				<ConnectedAppCard
					name='Instagram'
					desc='Find optimal hashtags and related terms for Instagram.'
					image='https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/300px-Instagram_logo_2022.svg.png'
				/>
				<ConnectedAppCard
					name='Facebook'
					desc='Find optimal hashtags and schedule posts for Facebook'
					image='https://upload.wikimedia.org/wikipedia/en/thumb/0/04/Facebook_f_logo_%282021%29.svg/300px-Facebook_f_logo_%282021%29.svg.png'
				/>
			</div>
		</div>
	);
}

export default ConnectedApps;
