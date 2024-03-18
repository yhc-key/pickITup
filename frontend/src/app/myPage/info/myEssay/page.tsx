"use client";
import { useRef, useState } from "react";

import { FaPlus, FaPen, FaChevronDown, FaChevronUp } from "react-icons/fa";

export default function MyEssay(): JSX.Element {
  const inputRef = useRef<HTMLInputElement>(null);
  const makeCanEditHandler = () => {};

  return (
    <div className="w-full relative pt-3 pr-3">
      <button className="absolute flex flex-row items-center border border-black rounded-lg right-3 py-2 px-4 gap-2">
        <div className="text-f5green-300">
          <FaPlus />
        </div>
        <div>과거 자소서 추가</div>
      </button>
      <div className="w-full flex flex-row border border-black rounded-lg mt-12 p-2 min-h-10 justify-between">
        <input
          ref={inputRef}
          placeholder="1. 당신이 입사한 이유가 무엇입니까?"
          className="w-full mr-10 outline-none"
        />
        <div className="flex flex-row gap-6 mr-4 text-lg">
          <button onClick={makeCanEditHandler}>
            <FaPen />
          </button>
          <button>
            <FaChevronDown />
          </button>
        </div>
      </div>
    </div>
  );
}
