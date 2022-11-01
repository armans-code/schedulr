import React from "react";
import { NavLink } from "react-router-dom";
import PostCard from "../components/posts/PostCard";
import { AiOutlineLoading3Quarters, AiOutlinePlus } from "react-icons/ai";
import { FACEBOOK_POSTS, TWITTER_TWEETS } from "../graphql/queries";
import { useQuery } from "@apollo/client";
import { useState } from "react";
import { useEffect } from "react";
import { useAuth } from "../auth/AuthContext";
import Summary from "../components/Summary";

function Dashboard() {
  const { currentUser } = useAuth();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState();
  const [sinceDate, setSinceDate] = useState("");
  const [posts, setPosts] = useState([]);

  const {
    data: fData,
    loading: fLoading,
    error: fErr,
  } = useQuery(FACEBOOK_POSTS, {
    variables: {
      queryFilter: {
        businessId: currentUser.uid,
      },
    },
  });

  const {
    data: tData,
    loading: tLoading,
    error: tErr,
  } = useQuery(TWITTER_TWEETS, {
    variables: {
      queryFilter: {
        businessId: currentUser.uid,
      },
    },
  });

  useEffect(() => {
    console.log(fData);
    console.log(tData);
  }, [fLoading, tLoading]);

  useEffect(() => {
    setLoading(tLoading || fLoading);
  }, [fLoading, tLoading]);

  useEffect(() => {
    setError({ ...fErr, ...tErr });
    console.log({ ...fErr, ...tErr });
  }, [fErr, tErr]);

  if (loading) {
    return (
      <div className="w-full h-screen flex flex-col items-center justify-center">
        <h1 className="animate-spin text-blue-600 text-6xl font-poppins font-extrabold text-center">
          <AiOutlineLoading3Quarters />
        </h1>
      </div>
    );
  }

  return (
    <div className="flex flex-col px-24">
      <div className="container mx-auto mt-10 flex flex-col gap-10">
        <div>
          {/* Page header */}
          <div className="sm:flex sm:justify-between sm:items-center mb-8">
            {/* Left: Title */}
            <div className="mb-4 sm:mb-0">
              <h1 className="text-2xl md:text-3xl text-gray-800 font-bold">
                Dashboard
              </h1>
              <p className="mt-2 text-gray-500">
                View Recent and Upcoming Posts
              </p>
            </div>
            {/* Right: Actions */}
            <div className="grid grid-flow-col sm:auto-cols-max justify-start sm:justify-end gap-2">
              {/* Add board button */}
              <NavLink
                className="p-2 lg:px-4 text-white rounded bg-blue-600 hover:bg-blue-700 transition flex items-center gap-3"
                to="/new-post"
              >
                New Post
                <AiOutlinePlus />
              </NavLink>
            </div>
          </div>
          <div className="border-t border-gray-200 gap-10">
            <div className="mt-10 grid grid-cols-3 gap-3">
              <div className="">
                <div className="bg-white rounded-md border border-gray-200 flex gap-3 p-3">
                  <div className="w-7">
                    <img
                      className="w-full h-full rounded-xl"
                      src="https://upload.wikimedia.org/wikipedia/en/thumb/0/04/Facebook_f_logo_%282021%29.svg/300px-Facebook_f_logo_%282021%29.svg.png"
                      alt="logo"
                    />
                  </div>
                  <div className="font-semibold text-lg text-gray-700">
                    Facebook
                  </div>
                </div>
                <div className="flex flex-col gap-5 pt-5 items-center">
                  <PostCard
                    description="Here are the biggest enterprise technology acquisitions
				            of 2021 so far, in reverse chronological order."
                    date="Oct 20, 4:30 PM"
                    image="https://images.unsplash.com/photo-1666067654612-603aeaaab991?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    past
                  />
                  <PostCard
                    description="Does coffee make you a better developer? It sure does help me! Order our coffee today!"
                    date="Oct 22, 1:00 PM"
                    image="https://images.unsplash.com/photo-1665806558925-930b7210d8bb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    past
                  />
                  <PostCard
                    description="On my way to work with my brand new dell laptop! This laptop makes developing easy as pie. Order with the link in my bio."
                    date="Oct 30, 6:00 PM"
                    image="https://images.unsplash.com/photo-1662581871665-f299ba8ace07?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=772&q=80"
                    past
                  />
                  <PostCard
                    description="Going to hit the road as soon as I'm done absorbing the Rocky Mountains! #nature #nofilter"
                    date="Nov 3, 10:00 AM"
                    image="https://images.unsplash.com/photo-1666052215005-2354fd24f5d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    past
                  />
                </div>
              </div>
              <div className="">
                <div className="bg-white rounded-md border border-gray-200 flex gap-3 p-3">
                  <div className="w-7">
                    <img
                      className="w-full h-full"
                      src="https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Twitter-logo.svg/440px-Twitter-logo.svg.png"
                      alt="logo"
                    />
                  </div>
                  <div className="font-semibold text-lg text-gray-700">
                    Twitter
                  </div>
                </div>
                <div className="flex flex-col gap-5 pt-5 items-center">
                  <PostCard
                    description="Here are the biggest enterprise technology acquisitions
				              of 2021 so far, in reverse chronological order."
                    date="Oct 20, 4:30 PM"
                    image="https://images.unsplash.com/photo-1666067654612-603aeaaab991?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    past
                  />
                  <PostCard
                    description="Does coffee make you a better developer? It sure does help me! Order our coffee today!"
                    date="Oct 22, 1:00 PM"
                    image="https://images.unsplash.com/photo-1665806558925-930b7210d8bb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    past
                  />
                  <PostCard
                    description="On my way to work with my brand new dell laptop! This laptop makes developing easy as pie. Order with the link in my bio."
                    date="Oct 30, 6:00 PM"
                    image="https://images.unsplash.com/photo-1662581871665-f299ba8ace07?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=772&q=80"
                    past
                  />
                  <PostCard
                    description="Going to hit the road as soon as I'm done absorbing the Rocky Mountains! #nature #nofilter"
                    date="Nov 3, 10:00 AM"
                    image="https://images.unsplash.com/photo-1666052215005-2354fd24f5d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    past
                  />
                </div>
              </div>
              <div className="">
                <div className="flex gap-3 bg-white rounded-md border border-gray-200 p-3">
                  <div className="w-7">
                    <img
                      className="w-full h-full rounded-xl"
                      src="https://upload.wikimedia.org/wikipedia/commons/thumb/9/95/Instagram_logo_2022.svg/300px-Instagram_logo_2022.svg.png"
                      alt="logo"
                    />
                  </div>
                  <div className="font-semibold text-lg text-gray-700">
                    Instagram
                  </div>
                </div>
                <div className="flex flex-col gap-5 pt-5 items-center">
                  <PostCard
                    description="Here are the biggest enterprise technology acquisitions
				            of 2021 so far, in reverse chronological order."
                    date="Oct 20, 4:30 PM"
                    image="https://images.unsplash.com/photo-1666052215005-2354fd24f5d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    past
                  />
                  <PostCard
                    description="On my way to work with my brand new dell laptop! This laptop makes developing easy as pie. Order with the link in my bio."
                    date="Oct 30, 6:00 PM"
                    image="https://images.unsplash.com/photo-1662581871665-f299ba8ace07?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=772&q=80"
                    past
                  />
                  <PostCard
                    description="Does coffee make you a better developer? It sure does help me! Order our coffee today!"
                    date="Oct 22, 1:00 PM"
                    image="https://images.unsplash.com/photo-1665806558925-930b7210d8bb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    past
                  />
                  <PostCard
                    description="Going to hit the road as soon as I'm done absorbing the Rocky Mountains! #nature #nofilter"
                    date="Nov 3, 10:00 AM"
                    image="https://images.unsplash.com/photo-1666067654612-603aeaaab991?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                    past
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
