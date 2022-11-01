import React, { useContext, useState } from 'react';
import { auth } from './firebase';
import { useEffect } from 'react';
import {
    BUSINESS,
    FACEBOOK_ACCOUNTS,
    INSTAGRAM_ACCOUNTS,
    TWITTER_ACCOUNTS,
} from '../graphql/queries';
import {
    onAuthStateChanged,
    signInWithEmailAndPassword,
    signOut,
} from 'firebase/auth';
import { useLazyQuery, useQuery } from '@apollo/client';

const AuthContext = React.createContext();

export function useAuth() {
    return useContext(AuthContext);
}

function setAccessToken(user) {
    user.getIdToken().then((token) =>
        localStorage.setItem('accessToken', token),
    );
}

export function AuthProvider({ children }) {
    const [currentUser, setCurrentUser] = useState();
    const [business, setBusiness] = useState();
    const [loading, setLoading] = useState(true);
    const [integrations, setIntegrations] = useState();

    const [getBusiness, { bLoading, error, data }] = useLazyQuery(BUSINESS, {
        fetchPolicy: 'network-only',
    });

    async function login(email, password) {
        const res = await signInWithEmailAndPassword(auth, email, password);
        return setAccessToken(res.user);
    }

    function logout() {
        return signOut(auth).then(() => Storage.removeItem('accessToken'));
    }

    useEffect(() => {
        const unsubscribe = onAuthStateChanged(auth, (user) => {
            setCurrentUser(user);
            setLoading(false);

            getBusiness({
                variables: {
                    businessId: user?.uid,
                },
            }).then((data) => {
                setBusiness(data?.data?.business);
            });
        });

        return () => {
            unsubscribe();
        };
    }, []);

    const value = {
        currentUser,
        business,
        login,
        logout,
        loading,
    };

    return (
        <AuthContext.Provider value={value}>
            {!loading && children}
        </AuthContext.Provider>
    );
}
