import { Route, Routes } from 'react-router-dom';
import './App.css';
import PrivateRoute from './components/layout/PrivateRotue';
import ConnectedApps from './pages/ConnectedApps';
import Dashboard from './pages/Dashboard';
import IntegrationCallback from './pages/IntegrationCallback';
import Login from './pages/Login';
import NewPost from './pages/NewPost';
import Register from './pages/Register';

function App() {
    return (
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route
                path="/new-post"
                element={
                    <PrivateRoute>
                        <NewPost />
                    </PrivateRoute>
                }
            />
            <Route
                path="/dashboard"
                element={
                    <PrivateRoute>
                        <Dashboard />
                    </PrivateRoute>
                }
            />
            <Route
                path="/connected-apps"
                element={
                    <PrivateRoute>
                        <ConnectedApps />
                    </PrivateRoute>
                }
            />
            <Route
                path="/integration-success"
                element={<IntegrationCallback />}
            />
        </Routes>
    );
}

export default App;
