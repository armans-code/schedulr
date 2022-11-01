import React from "react";

function Summary(props) {
    const {title, amount} = props;
  return (
    <div className="flex flex-col col-span-full sm:col-span-6 xl:col-span-4 bg-white shadow-lg rounded-lg border border-gray-200">
      <div className="px-5 pt-5 pb-5">
        <h2 className="text-lg font-semibold text-gray-800 mb-2">{title}</h2>
        <div className="text-3xl font-bold text-gray-800 mr-2">{amount}</div>
      </div>
    </div>
  );
}

export default Summary;
