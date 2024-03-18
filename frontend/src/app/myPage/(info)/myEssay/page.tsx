"use client";
import { Fragment, MutableRefObject, useRef, useState } from "react";

import { cloneDeep } from "lodash";
import {
  FaPlus,
  FaPen,
  FaChevronDown,
  FaChevronUp,
  FaTrash,
} from "react-icons/fa";
import Link from "next/link";
interface Essay {
  company: string;
  title: string;
  content: string;
}

const myEssays: Essay[][] = [
  [
    {
      company: "LG전자",
      title: "입사하면 뭐하고 싶어요?(500자)",
      content: "블라블라블라",
    },
    {
      company: "sk하이닉스",
      title: "입사한 계기와 자기소개서(700자)",
      content: "블라블라",
    },
  ],
  [
    {
      company: "LG전자",
      title: "본인의 장단점을 적어주세요(500자)",
      content: "블라블라블라",
    },
  ],
]; // essay 목록 가져오기
const booleanArray: boolean[][] = myEssays.map((subArray) =>
  subArray.map((essay: Essay, index: number): boolean =>
    index == 0 ? true : false
  )
); // essay 목록에 해당하는 boolean 배열 만들기

export default function MyEssay(): JSX.Element {
  const inputRef = useRef<HTMLInputElement>(null);
  const [dropDownClickList, setdropDownClickList]: [
    boolean[],
    React.Dispatch<React.SetStateAction<boolean[]>>,
  ] = useState<boolean[]>(
    Array.from({ length: myEssays.length }, (): boolean => false)
  ); // 드롭다운 활성화 된 부분 boolean 체크
  const [myEssayActive, setMyEssayActive]: [
    boolean[][],
    React.Dispatch<React.SetStateAction<boolean[][]>>,
  ] = useState(booleanArray);
  const makeCanEditHandler = () => {};

  const makeCanEditTitleContentHandler = () => {};

  const dropDownClickHandler = (index: number) => {
    const arr: boolean[] = [...dropDownClickList];
    arr[index] = !arr[index];
    setdropDownClickList(arr);
    return;
  }; // 클릭시 tf 바꾸기

  const clickEssayHandler = (essayIndex: number, companyIndex: number) => {
    const tmpEssays = cloneDeep(booleanArray);
    tmpEssays[essayIndex].forEach(
      (value: boolean, index: number) => (tmpEssays[essayIndex][index] = false)
    );
    tmpEssays[essayIndex][companyIndex] = true;
    setMyEssayActive(tmpEssays);
  };
  <span>내가 찜한 채용공고</span>;
  return (
    <div className="w-full relative pt-3 pr-3">
      <Link
        href="/myPage/addEssay"
        className="absolute flex flex-row items-center border border-black rounded-lg right-3 py-2 px-4 gap-2"
      >
        <div className="text-f5green-300">
          <FaPlus />
        </div>
        <div>과거 자소서 추가</div>
      </Link>
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
          <button onClick={() => dropDownClickHandler(0)}>
            {dropDownClickList[0] ? <FaChevronUp /> : <FaChevronDown />}
          </button>
        </div>
      </div>
      <div className={`${dropDownClickList[0] ? "" : "hidden"}`}>
        <div className="flex flex-row m-1">
          {myEssays[0].map((essay: Essay, index: number) => {
            return (
              <button
                type="button"
                onClick={() => clickEssayHandler(0, index)}
                key="index"
                className={`ml-1 ${myEssayActive[0][index] ? "text-f5green-300" : ""}`}
              >
                {essay.company}
              </button>
            );
          })}
        </div>
        {myEssays[0].map((essay: Essay, index: number) => {
          if (!myEssayActive[0][index]) {
            return;
          }
          return (
            <Fragment key={index}>
              <div className="relative flex flex-row w-full border text-sm  border-black rounded-t-lg mt-2 p-2 min-h-16">
                <p>{essay.title}</p>
                <button className="absolute right-2 text-2xl m-2">
                  <FaTrash />
                </button>
              </div>
              <div className="relative w-full flex flex-row border text-sm border-black rounded-b-lg p-2 min-h-40">
                <p>{essay.content}</p>
                <button
                  onClick={makeCanEditTitleContentHandler}
                  className="text-lg absolute right-2 bottom-0 m-3"
                >
                  <FaPen />
                </button>
              </div>
            </Fragment>
          );
        })}
      </div>

      <div className="w-full flex flex-row border border-black rounded-lg mt-2 p-2 min-h-10 justify-between">
        <input
          ref={inputRef}
          placeholder="2. 본인의 장점과 단점을 적어보시오."
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
