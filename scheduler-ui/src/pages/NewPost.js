import React, { useState } from 'react';
import Emoji from '../components/new-post/Emoji';
import Hashtag from '../components/new-post/Hashtag';

function NewPost() {
    const [caption, setCaption] = useState('');
    const [radio, setRadio] = useState('now');
    const [meridiem, setMeridiem] = useState('');
    const [time, setTime] = useState('4:30');

    const handleRadioChange = (value) => {
        setRadio(value);
    };

    // TODO: improve time input
    const handleTimeChange = (input) => {
        if (input.length === 2) {
            setTime(input + ':');
        } else {
            setTime(input);
        }
    };

    return (
        <div className="px-24 flex flex-col">
            <h1 className="text-3xl mt-8 font-medium">Create New Post</h1>
            <div class="mt-12">
                <div className="flex w-full items-center justify-between">
                    <p class="font-medium text-gray-700 tracking-wide">
                        Post Caption
                    </p>
                    <p class="text-gray-500">{caption.length} characters</p>
                </div>
                <textarea
                    class="w-full mt-2 text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-400"
                    type=""
                    rows={4}
                    value={caption}
                    onChange={(e) => setCaption(e.target.value)}
                    placeholder="My post is about..."
                />
            </div>
            <div class="mt-12">
                <label class="font-medium text-gray-700 tracking-wide">
                    Recommended Hashtags:
                </label>
                <div class="flex gap-4 w-full mt-2 text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-400">
                    <Hashtag text="nature" />
                    <Hashtag text="waterfall" />
                    <Hashtag text="sky" />
                    <Hashtag text="alaska" />
                    <Hashtag text="trending2022" />
                </div>
            </div>
            <div class="mt-12">
                <label class="font-medium text-gray-700 tracking-wide">
                    Related emojis
                </label>
                <div class="flex gap-8 w-full mt-2 text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-400">
                    <Emoji emoji="🌲" />
                    <Emoji emoji="🌷" />
                    <Emoji emoji="🦊" />
                    <Emoji emoji="🌅" />
                    <Emoji emoji="🌼" />
                    <Emoji emoji="🌇" />
                    <Emoji emoji="🦁" />
                    <Emoji emoji="🦋" />
                    <Emoji emoji="🐢" />
                    <Emoji emoji="🍁" />
                </div>
            </div>
            <div class="mt-12 flex items-end justify-between mb-10">
                <div>
                    <label class="font-medium text-gray-700 tracking-wide">
                        Post Date & Time
                    </label>
                    <div class="flex flex-col mt-4">
                        <div class="flex items-center mb-4">
                            <input
                                id="now-radio-1"
                                type="radio"
                                value="now"
                                checked={radio === 'now'}
                                onChange={(e) =>
                                    handleRadioChange(e.target.value)
                                }
                                class="w-4 h-4 bg-gray-100 border-gray-300"
                            />
                            <label
                                for="now-radio-1"
                                class="ml-2 font-medium text-gray-900"
                            >
                                Post now
                            </label>
                        </div>
                        <div class="flex items-center">
                            <input
                                id="default-radio-2"
                                type="radio"
                                value="schedule"
                                checked={radio === 'schedule'}
                                onChange={(e) =>
                                    handleRadioChange(e.target.value)
                                }
                                class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300"
                            />
                            <div class="ml-2 flex gap-3">
                                <input
                                    name="date"
                                    id="date"
                                    // value={values.date}
                                    type="date"
                                    // onChange={handleChange}
                                    // onBlur={handleBlur}
                                    className="outline-none rounded-lg border px-4 py-2 "
                                />
                                {/* TODO: fix up time & date - still in progress */}
                                <div class="flex items-center w-min justify-start bg-white border rounded-lg">
                                    <input
                                        id="time"
                                        name="time"
                                        type="text"
                                        value={time}
                                        maxLength={5}
                                        onChange={(e) =>
                                            handleTimeChange(e.target.value)
                                        }
                                        className="outline-none rounded-lg w-16 px-2 py	-2"
                                        pattern="(1[012]|0[1-9]):[0-5][0-9]"
                                        title="Please enter in an HH:mm format."
                                    />
                                    <select
                                        id="zone"
                                        value={meridiem}
                                        onChange={(e) =>
                                            setMeridiem(e.target.value)
                                        }
                                        className="py-2 rounded-lg focus:outline-none"
                                    >
                                        <option defaultValue="PM">PM</option>
                                        <option value="AM">AM</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <button className="p-2 lg:px-4 md:mx-2 text-white rounded bg-blue-600 hover:bg-blue-700 transition">
                        Submit Post
                    </button>
                </div>
            </div>
        </div>
    );
}

export default NewPost;
