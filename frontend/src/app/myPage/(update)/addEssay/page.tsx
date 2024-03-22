"use client";

import useEssayStore, { EssayState } from "@/store/essayStore";
import { useRouter } from "next/navigation";
import { Fragment, useRef, useState } from "react";
import { FaMinus, FaPlus } from "react-icons/fa";

interface Title {
  id: number;
  title: string;
}

export default function AddEssay() {
  const textareaRefs = useRef<(HTMLTextAreaElement | null)[]>([]);
  const router = useRouter();
  const essayTitles: Title[] = useEssayStore(
    (state: EssayState) => state.essayTitles
  );
  const [titleActive, setTitleActive] = useState<boolean[]>([]);

  const submitHandler = () => {};

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
      {essayTitles.length !== 0 && (
        <form
          onSubmit={submitHandler}
          className="border border-f5gray-500 rounded-2xl h-full py-6 px-16 flex flex-col relative"
        >
          <div className="flex flex-row items-center ml-4 ">
            <span>지원한 회사 :</span>
            <input
              placeholder="회사명을 입력해주세요"
              className="ml-4 focus:bg-white focus:outline-f5green-300 "
            />
          </div>
          {essayTitles.map((title: Title, index: number) => (
            <Fragment key={title.id}>
              <div
                className={`w-full flex flex-row border border-black rounded-lg p-2 min-h-10 justify-between ${index === 0 ? "mt-12" : "mt-2"}`}
              >
                <span className="w-full mr-10 outline-none">{`${index + 1}. ${title.title}`}</span>
                <div className="flex flex-row gap-6 mr-4 text-lg">
                  <button type="button" onClick={() => addClickHandler(index)}>
                    {!titleActive[index] ? (
                      <FaPlus className="text-f5green-300" />
                    ) : (
                      <FaMinus className="text-f5green-300" />
                    )}
                  </button>
                </div>
              </div>
              {titleActive?.[index] && (
                <Fragment>
                  <textarea
                    placeholder="회사별 자세한 항목을 써주세요"
                    className="w-full p-2 mt-2 text-sm border border-black rounded-t-lg border-b-white min-h-16"
                  />
                  <textarea
                    placeholder="자기소개서 내용을 써주세요"
                    className="w-full p-2 text-sm border border-black rounded-b-lg min-h-40"
                  />
                </Fragment>
              )}
            </Fragment>
          ))}
          <div className="absolute bottom-0 right-0 mb-6 mr-6">
            <button
              type="submit"
              className="px-6 py-2 mx-4 text-white rounded-lg bg-f5red-300"
            >
              취소하기
            </button>
            <button
              type="reset"
              className="px-6 py-2 mx-4 text-white rounded-lg bg-f5green-300"
            >
              등록하기
            </button>
          </div>
        </form>
      )}
    </Fragment>
  );
}
