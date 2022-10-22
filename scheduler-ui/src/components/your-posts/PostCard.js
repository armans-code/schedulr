import React from 'react';

function PostCard(props) {
    const { image, date, description, twitter, instagram, facebook, past } =
        props;
    const buttonStyle = past
        ? 'bg-gray-500 hover:bg-gray-600'
        : 'bg-blue-700 hover:bg-blue-800';
    return (
        <div class="min-w-max bg-white rounded-lg border border-gray-200 shadow-md ">
            <div className="w-80">
                <a>
                    <img
                        class="rounded-t-lg w-full h-48 object-cover"
                        src={image}
                        alt=""
                    />
                </a>
                <div class="p-5">
                    <p class="mb-3 font-normal">Scheduled for {date}</p>
                    <p class="mb-3 font-normal text-gray-700">{description}</p>
                    <div className="flex items-center justify-between">
                        <div className="flex gap-3">
                            {twitter && (
                                <img
                                    class="h-8"
                                    src="https://cdn4.iconfinder.com/data/icons/social-media-icons-the-circle-set/48/twitter_circle-1024.png"
                                />
                            )}
                            {instagram && (
                                <img
                                    class="h-8"
                                    src="https://www.montessoriivyleague.com/wp-content/uploads/2018/10/instagram-logo-circle-768x768.png"
                                />
                            )}
                            {facebook && (
                                <img
                                    class="h-8"
                                    src="https://www.edigitalagency.com.au/wp-content/uploads/Facebook-logo-blue-circle-large-white-f.png"
                                />
                            )}
                        </div>
                        <a
                            class={`hover:cursor-pointer inline-flex items-center py-2 px-3 text-sm font-medium text-center text-white  rounded-lg focus:ring-4 focus:outline-none transition ${buttonStyle}`}
                        >
                            {past ? <p>View</p> : <p> Edit</p>}
                            <svg
                                aria-hidden="true"
                                class="ml-2 -mr-1 w-4 h-4"
                                fill="currentColor"
                                viewBox="0 0 20 20"
                                xmlns="http://www.w3.org/2000/svg"
                            >
                                <path
                                    fill-rule="evenodd"
                                    d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
                                    clip-rule="evenodd"
                                ></path>
                            </svg>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default PostCard;
