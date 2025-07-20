import React from 'react';
import { Toaster } from 'react-hot-toast';
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { RedirectToSignIn, SignedIn, SignedOut } from '@clerk/clerk-react';

import LandingPage from './pages/LandingPage/LandingPage';
import Dashboard  from './pages/Dashboard';
import MainPage   from './pages/MainPage';
import PreviewPage from './pages/PreviewPage';
import Menubar    from './components/Menubar'; 
import UserSyncHandler from './components/UserSyncHandler';

const App = () => {
  return (
    <BrowserRouter basename="/invoice-generator">
      <UserSyncHandler />
      <Menubar />
      <Toaster />
      <Routes>
        <Route path="/" element={<LandingPage />} />

        <Route
          path="/dashboard"
          element={
            <>
              <SignedIn>
                <Dashboard />
              </SignedIn>
              <SignedOut>
                <RedirectToSignIn />
              </SignedOut>
            </>
          }
        />

        <Route
          path="/generate"
          element={
            <>
              <SignedIn>
                <MainPage />
              </SignedIn>
              <SignedOut>
                <RedirectToSignIn />
              </SignedOut>
            </>
          }
        />

        <Route
          path="/preview"
          element={
            <>
              <SignedIn>
                <PreviewPage />
              </SignedIn>
              <SignedOut>
                <RedirectToSignIn />
              </SignedOut>
            </>
          }
        />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
