import { useLazyQuery, useMutation } from "@apollo/client";
import React, { useEffect, useState } from "react";
import { useAuth } from "../auth/AuthContext";
import Emoji from "../components/new-post/Emoji";
import Hashtag from "../components/new-post/Hashtag";
import axios from "axios";
import Checkbox from "@mui/material/Checkbox";
import {
  CREATE_FACEBOOK_POST,
  CREATE_INSTAGRAM_POST,
  CREATE_TWITTER_POST,
} from "../graphql/mutations";
import {
  FACEBOOK_ACCOUNTS,
  INSTAGRAM_ACCOUNTS,
  TWITTER_ACCOUNTS,
} from "../graphql/queries";
import { FormControlLabel } from "@mui/material";
import { AiFillCloseCircle, AiOutlinePlus } from "react-icons/ai";
import { NavLink } from "react-router-dom";

function NewPost() {
  const [caption, setCaption] = useState("");
  const [radio, setRadio] = useState("now");
  const [meridiem, setMeridiem] = useState("");
  const [time, setTime] = useState("4:30");

  const [tags, setTags] = useState();
  const [emojis, setEmojis] = useState("");

  const [twtCheck, setTwtCheck] = useState(false);
  const [fbCheck, setFbCheck] = useState(false);
  const [igCheck, setIgCheck] = useState(false);

  const { business } = useAuth();

  const [getFacebookAccount] = useLazyQuery(FACEBOOK_ACCOUNTS);
  const [getTwitterAccount] = useLazyQuery(TWITTER_ACCOUNTS);
  const [getInstagramAccount] = useLazyQuery(INSTAGRAM_ACCOUNTS);
  const [createFacebookPost] = useMutation(CREATE_FACEBOOK_POST);
  const [createTwitterTweet] = useMutation(CREATE_TWITTER_POST);
  const [createInstagramPost] = useMutation(CREATE_INSTAGRAM_POST);

  useEffect(() => {
    console.log(fbCheck);
  }, [fbCheck]);

  const handlePostInstagram = () => {
    getInstagramAccount({
      variables: {
        businessId: business?.id,
      },
    }).then((data) => {
      console.log(data?.data?.instagramAccounts[0]);
      const instagramAccountId = data?.data?.instagramAccounts[0]?.id;
      createInstagramPost({
        variables: {
          CreateInstagramPostInput: {
            businessId: business?.id,
            instagramAccountId: instagramAccountId,
            caption: caption,
            imageUrl:
              "https://images.unsplash.com/photo-1498050108023-c5249f4df085?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1472&q=80",
          },
        },
      }).then((data) => {
        console.log(data);
      });
    });
  };

  const handlePostFacebook = () => {
    getFacebookAccount({
      variables: {
        businessId: business?.id,
      },
    }).then((data) => {
      const facebookAccountId = data?.data?.facebookAccounts[0].id;
      createFacebookPost({
        variables: {
          CreateFacebookPostInput: {
            businessId: business?.id,
            facebookAccountId: facebookAccountId,
            // scheduledPublishTime: '2022-10-30T15:54:22.844-04:00',
            message: caption,
          },
        },
      }).then((data) => {
        console.log(data);
      });
    });
  };

  const handlePostTwitter = () => {
    getTwitterAccount({
      variables: {
        businessId: business?.id,
      },
    }).then((data) => {
      console.log(data?.data?.twitterAccounts[0].id);
      const twitterAccountId = data?.data?.twitterAccounts[0].id;
      const twitterUserId = data?.data?.twitterAccounts[0].twitterId;
      createTwitterTweet({
        variables: {
          CreateTwitterTweetInput: {
            businessId: business?.id,
            twitterAccountId: twitterAccountId,
            message: caption,
            userId: twitterUserId,
            // imageUrl: 'https://images.unsplash.com/photo-1498050108023-c5249f4df085?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1472&q=80',
            // scheduledPublishTime: '2022-10-31T10:32:22.844-04:00',
          },
        },
      }).then((data) => {
        console.log(data);
      });
    });
  };

  const handleSubmit = () => {
    console.log("hello");
    if (fbCheck) handlePostFacebook();
    if (twtCheck) handlePostTwitter();
    // handlePostInstagram();
  };

  // TODO: improve time input
  const handleTimeChange = (input) => {
    if (input.length === 2) {
      setTime(input + ":");
    } else {
      setTime(input);
    }
  };

  const handleTags = () => {
    axios
      .get("http://localhost:8000/find-tags", {
        params: { caption: caption },
      })
      .then((data) => {
        console.log(data?.data?.tags);
        setTags(data?.data?.tags);
      });
  };

  const handleEmoji = () => {
    try {
      axios
        .get("https://emoji-api.com/emojis", {
          params: {
            search: tags[0],
            access_key: process.env.REACT_APP_EMOJI_API_KEY,
          },
        })
        .then((data) => {
          console.log(data?.data);
          setEmojis(data?.data[0].character);
        });
    } catch (error) {
      console.log(error);
    }
  };

  return (
    <div className="container mx-auto flex flex-col">
      <div className="mt-10 sm:flex sm:justify-between sm:items-center mb-8">
        {/* Left: Title */}
        <div className="mb-4 sm:mb-0">
          <h1 className="text-2xl md:text-3xl text-gray-800 font-bold">
            New Post
          </h1>
          <p className="mt-2 text-gray-500">
            Send/Schedule Posts to Facebook, Twitter, and Instagram
          </p>
        </div>
        <div className="grid grid-flow-col sm:auto-cols-max justify-start sm:justify-end gap-2">
          {/* Add board button */}
          <NavLink
            className="p-2 lg:px-4 text-white rounded bg-red-600 hover:bg-red-700 transition flex items-center gap-3"
            to="/dashboard"
          >
            Cancel
            <AiFillCloseCircle />
          </NavLink>
        </div>
      </div>
      <div className="grid grid-cols-2 gap-20">
        <div>
          <div className="mt-12">
            <div className="flex items-center justify-between">
              <p className="font-medium">Post Caption</p>
              <p className="text-gray-500">{caption.length} characters</p>
            </div>
            <textarea
              className="w-full mt-2 text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-400"
              type=""
              rows={4}
              value={caption}
              onChange={(e) => setCaption(e.target.value)}
              placeholder="My post is about..."
            />
          </div>
          <div class="w-full flex flex-col mt-8">
            <label class="inline-block font-medium mb-2">Image Upload</label>
            <div class="w-full border border-gray-300 rounded-lg">
              <div class="m-4">
                <div class="flex w-full">
                  <label class="w-full flex flex-col  h-32 border-4 border-blue-200 border-dashed hover:bg-gray-100 hover:border-gray-300">
                    <div class="flex flex-col items-center justify-center pt-7">
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        class=" w-8 h-8 text-gray-400 group-hover:text-gray-600"
                        fill="none"
                        viewBox="0 0 24 24"
                        stroke="currentColor"
                      >
                        <path
                          stroke-linecap="round"
                          stroke-linejoin="round"
                          stroke-width="2"
                          d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12"
                        />
                      </svg>
                      <p class="pt-1 text-sm tracking-wider text-gray-400 group-hover:text-gray-600">
                        Attach an image (.png, .jpeg, .jpg, etc.)
                      </p>
                    </div>
                    <input type="file" class="opacity-0" />
                  </label>
                </div>
              </div>
            </div>
          </div>
          <div className="mt-12">
            <div className="flex items-center gap-3">
              <label className="font-medium">Related Emojis</label>
              <button
                className="border shadow px-3 py-1 rounded hover:bg-gray-50"
                onClick={handleEmoji}
              >
                Generate
              </button>
            </div>
            <div className="flex gap-8  mt-2 text-base px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-400">
              {/* {emojis?.map((emoji) => ( */}
              <button onClick={() => setCaption(`${caption} ${emojis}`)}>
                <div className="text-2xl font-semibold inline-block py-1 px-2 uppercase rounded shadow-lg bg-white hover:bg-gray-100 transition duration-200 last:mr-0 mr-1">
                  {emojis}
                </div>
              </button>
            </div>
          </div>
          <div className="mt-12">
            <div className="flex items-center gap-3">
              <label className="font-medium">Recommended Hashtags</label>
              <button
                className="border shadow px-3 py-1 rounded hover:bg-gray-50"
                onClick={handleTags}
              >
                Generate
              </button>
            </div>
            <div className="overflow-auto py-4 flex gap-4  mt-2 text-base px-4 border border-gray-300 rounded-lg focus:outline-none focus:border-blue-400">
              {tags?.map((tag) => {
                if (tag.length > 3)
                  return (
                    <div
                      onClick={() => {
                        setCaption(`${caption} #${tag}`);
                      }}
                    >
                      <Hashtag text={tag} />
                    </div>
                  );
              })}
            </div>
          </div>
        </div>
        <div className="">
          <div className="mt-12">
            <label className="text-xs font-semibold text-gray-400 uppercase mb-1">
              Platforms
            </label>
            <div className="flex flex-col">
              <FormControlLabel
                className="font-medium text-gray-800"
                label="Twitter"
                control={
                  <Checkbox
                    checked={twtCheck}
                    onChange={(e) => {
                      setTwtCheck(e.target.checked);
                    }}
                  />
                }
              />
              <FormControlLabel
                className="font-medium text-gray-800"
                label="Facebook"
                control={
                  <Checkbox
                    checked={fbCheck}
                    onChange={(e) => {
                      setFbCheck(e.target.checked);
                    }}
                  />
                }
              />
              <FormControlLabel
                className="font-medium text-gray-800"
                label="Instagram"
                control={
                  <Checkbox
                    checked={igCheck}
                    onChange={(e) => {
                      setIgCheck(e.target.checked);
                    }}
                  />
                }
              />
            </div>
          </div>
          <div className="mt-12 flex items-end justify-between mb-10">
            <div>
              <label className="text-xs font-semibold text-gray-400 uppercase mb-1">
                Post Date & Time
              </label>
              <div className="flex flex-col mt-4">
                <div className="flex items-center mb-4">
                  <input
                    id="now-radio-1"
                    type="radio"
                    value="now"
                    checked={radio === "now"}
                    onChange={(e) => setRadio(e.target.value)}
                    className="w-4 h-4 bg-gray-100 border-gray-300"
                  />
                  <label
                    for="now-radio-1"
                    className="ml-2 font-medium text-gray-800"
                  >
                    Post now
                  </label>
                </div>
                <div className="flex items-center">
                  <input
                    id="default-radio-2"
                    type="radio"
                    value="schedule"
                    checked={radio === "schedule"}
                    onChange={(e) => setRadio(e.target.value)}
                    className="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300"
                  />
                  <div className="ml-2 flex gap-3">
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
                    <div className="flex items-center w-min justify-start bg-white border rounded-lg">
                      <input
                        id="time"
                        name="time"
                        type="text"
                        value={time}
                        maxLength={5}
                        onChange={(e) => handleTimeChange(e.target.value)}
                        className="outline-none rounded-lg w-16 px-2 py	-2"
                        pattern="(1[012]|0[1-9]):[0-5][0-9]"
                        title="Please enter in an HH:mm format."
                      />
                      <select
                        id="zone"
                        value={meridiem}
                        onChange={(e) => setMeridiem(e.target.value)}
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
          </div>
          <div>
            <button
              onClick={handleSubmit}
              className="p-2 lg:px-4 md:mx-2 text-white rounded bg-blue-600 hover:bg-blue-700 transition"
            >
              Submit Post
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default NewPost;
