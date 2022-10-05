import React from 'react';
import { Navigate } from 'react-router-dom';
import Layout from './Layout';

const PrivateRoute = ({ children }) => {

	// if (!currentUser) {
		// return <Navigate to='/login' />;
	// }
	return <Layout>{children}</Layout>;
};

export default PrivateRoute;
