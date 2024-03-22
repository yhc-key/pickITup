"use client";

import useEssayStore from "@/store/essayStore";
import { Fragment, useState } from "react";
import { FaChevronUp } from "react-icons/fa";

interface Title {
  id: number;
  title: string;
}

export default function AddEssay() {
  const submitHandler = () => {};
  const [titleActive, setTitleActive] = useState<boolean[]>([]);
  const essayTitles = useEssayStore((state) => state.essayTitles);
  const addClickHandler = (index: number) => {
    const arr: boolean[] = [...titleActive];
    arr[index] = !arr[index];
    setTitleActive(arr);
    return;
  };
  return (
    <Fragment>
      {essayTitles.length === 0 && (
        <div className="flex justify-center items-center text-lg text-f5green-500 h-full">
          먼저 자소서 항목을 추가해주세요
        </div>
      )}
      {essayTitles.length !== 0 &&
      <form
        onSubmit={submitHandler}
        className="border border-f5gray-500 rounded-2xl h-full py-6 pl-16 flex flex-col"
      >
        <div className="flex flex-row items-center ">
          <span>지원한 회사 :</span>
          <input placeholder="회사명을 입력해주세요" className="ml-4" />
        </div>
         essayTitles.map((title: Title, index: number) => {
            return (
              <Fragment key={title.id}>
                <div
                  className={`w-full flex flex-row border border-black rounded-lg p-2 min-h-10 justify-between ${index === 0 ? "mt-12" : "mt-2"}`}
                >
                  <span className="w-full mr-10 outline-none">{`${index + 1}. ${title.title}`}</span>
                  <div className="flex flex-row gap-6 mr-4 text-lg">
                    <button onClick={() => addClickHandler(index)}>
                      {titleActive[index] ? <FaChevronUp /> : ""}
                    </button>
                  </div>
                </div>
              </Fragment>
            );
          })}
      </form>
    </Fragment>
  );
}
