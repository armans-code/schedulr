import React from 'react';
import { NavLink } from 'react-router-dom';
import PostCard from '../components/your-posts/PostCard';
import { AiOutlinePlus } from 'react-icons/ai';

function Dashboard() {
    return (
        <div className="flex flex-col px-24">
            <div className="mt-10 flex flex-col gap-20">
                <div>
                    <div className="w-full flex items-center justify-between">
                        <h1 className="text-3xl font-medium">
                            Scheduled posts
                        </h1>
                        <NavLink
                            className="p-2 lg:px-4 md:mx-2 text-white rounded bg-blue-600 hover:bg-blue-700 transition flex items-center gap-3"
                            to="/new-post"
                        >
                            New Post
                            <AiOutlinePlus />
                        </NavLink>
                    </div>
                    <div className="flex overflow-x-auto hide-scroll space-x-8 py-8">
                        <PostCard
                            description="Here are the biggest enterprise technology acquisitions
				of 2021 so far, in reverse chronological order."
                            date="Oct 20, 4:30 PM"
                            image="https://images.unsplash.com/photo-1666067654612-603aeaaab991?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                            instagram
                        />
                        <PostCard
                            description="Does coffee make you a better developer? It sure does help me! Order our coffee today!"
                            date="Oct 22, 1:00 PM"
                            image="https://images.unsplash.com/photo-1665806558925-930b7210d8bb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                            twitter
                            facebook
                        />
                        <PostCard
                            description="On my way to work with my brand new dell laptop! This laptop makes developing easy as pie. Order with the link in my bio."
                            date="Oct 30, 6:00 PM"
                            image="https://images.unsplash.com/photo-1662581871665-f299ba8ace07?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=772&q=80"
                            instagram
                            twitter
                            facebook
                        />
                        <PostCard
                            description="Going to hit the road as soon as I'm done absorbing the Rocky Mountains! #nature #nofilter"
                            date="Nov 3, 10:00 AM"
                            image="https://images.unsplash.com/photo-1666052215005-2354fd24f5d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                            facebook
                            instagram
                        />
                    </div>
                </div>
                <div>
                    <h1 className="text-3xl font-medium">Past posts</h1>
                    <div className="flex overflow-x-auto hide-scroll space-x-8 py-8">
                        <PostCard
                            description="Here are the biggest enterprise technology acquisitions
				of 2021 so far, in reverse chronological order."
                            date="Oct 20, 4:30 PM"
                            image="https://images.unsplash.com/photo-1666067654612-603aeaaab991?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                            instagram
                            past
                        />
                        <PostCard
                            description="Does coffee make you a better developer? It sure does help me! Order our coffee today!"
                            date="Oct 22, 1:00 PM"
                            image="https://images.unsplash.com/photo-1665806558925-930b7210d8bb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                            twitter
                            facebook
                            past
                        />
                        <PostCard
                            description="On my way to work with my brand new dell laptop! This laptop makes developing easy as pie. Order with the link in my bio."
                            date="Oct 30, 6:00 PM"
                            image="https://images.unsplash.com/photo-1662581871665-f299ba8ace07?ixlib=rb-1.2.1&ixid=MnwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=772&q=80"
                            instagram
                            twitter
                            facebook
                            past
                        />
                        <PostCard
                            description="Going to hit the road as soon as I'm done absorbing the Rocky Mountains! #nature #nofilter"
                            date="Nov 3, 10:00 AM"
                            image="https://images.unsplash.com/photo-1666052215005-2354fd24f5d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"
                            facebook
                            instagram
                            past
                        />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Dashboard;
