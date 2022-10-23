import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../../auth/AuthContext';
import Layout from './Layout';

const PrivateRoute = ({ children }) => {
    const { currentUser } = useAuth();

    if (!currentUser) {
        return <Navigate to="/login" />;
    }
    return <Layout>{children}</Layout>;
};

export default PrivateRoute;
