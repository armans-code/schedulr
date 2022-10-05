import React from 'react';
import { NavLink } from 'react-router-dom';

function HeaderLink(props) {
	const { children } = props;
	return (
		<NavLink
			to={props?.to}
			className={({ isActive }) =>
				isActive
					? 'p-2 lg:px-4 md:mx-2 text-white rounded bg-blue-600'
					: 'p-2 lg:px-4 md:mx-2 text-gray-600 rounded hover:bg-gray-200 hover:text-gray-700 transition-colors duration-300'
			}
		>
			{children}
		</NavLink>
	);
}

export default HeaderLink;
