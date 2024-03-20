"use client";
import { Fragment, MutableRefObject, useEffect, useRef, useState } from "react";
import {
  FaPlus,
  FaPen,
  FaChevronDown,
  FaChevronUp,
  FaTrash,
} from "react-icons/fa";
import { cloneDeep } from "lodash";
import Link from "next/link";
import Modal from "@/components/modal";
interface Essay {
  company: string;
  title: string;
  content: string;
}

interface Title {
  id: number;
  title: string;
}

const apiAddress = "https://spring.pickITup.online/self/main";

const dummyTitles: Title[] = [
  { id: 1, title: "당신이 입사한 이유가 무엇입니까?" },
  {
    id: 2,
    title: "본인의 장점과 단점을 적어보시오",
  },
];
const myEssays: Essay[][] = [
  [
    {
      company: "LG전자",
      title: "입사하면 뭐하고 싶어요?(500자)",
      content: "블라블라블라",
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

export default function MyEssay(): JSX.Element {
  const inputRef = useRef<HTMLInputElement>(null);
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [myEssayActive, setMyEssayActive] = useState<boolean[][]>([]); //서브 타이틀 나오면서 뜨는 회사 명중 클릭된 것
  const [titleActive, setTitleActive] = useState<boolean[]>([]);
  //타이틀 액티브 되었다는 것은 드롭다운이 내려간다는 뜻
  const [titles, setTitles] = useState(dummyTitles);
  const [essays, setEssays] = useState<Essay[][]>(myEssays);

  const makeCanEditHandler = () => {};

  const makeCanEditTitleContentHandler = () => {};

  const dropDownClickHandler: (index: number) => void = (
    index: number
  ): void => {
    const arr: boolean[] = [...titleActive];
    arr[index] = !arr[index];
    setTitleActive(arr);
    return;
  }; // 클릭시 tf 바꾸기

  const clickEssayHandler = (essayIndex: number, companyIndex: number) => {
    const tmpEssays: boolean[][] = cloneDeep(myEssayActive);
    tmpEssays[essayIndex].forEach(
      (value: boolean, index: number) => (tmpEssays[essayIndex][index] = false)
    );
    tmpEssays[essayIndex][companyIndex] = true;
    setMyEssayActive(tmpEssays);
  };

  const clickAddEssayHandler = () => {};

  useEffect(() => {
    const essayListfetchData = async () => {
      try {
        const res: Response = await fetch(apiAddress, {
          headers: {
            Authorization:
              "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlJPTEVfVVNFUiIsImV4cCI6MTcxMDgzODI0Mn0.hIHFh_B-VnyIw3nlRAgENNx8igdbI-TGNyNpP8SuFUc",
          },
        });
        if (!res.ok) {
          throw new Error("Failed to fetch data");
        }
        setTitles(await res.json());
      } catch (error) {
        console.log(error);
      }
    };
    essayListfetchData();
  }, []); // 메인 타이틀 받아오기

  useEffect(() => {
    const fetchEssaysdata = async () => {
      try {
        const urls: string[] = [];
        titles.map((title: Title): void => {
          urls.push(`${apiAddress}/${title.id}/sub`);
        });

        const res: Response[] = await Promise.all(
          urls.map((url: string): Promise<Response> => fetch(url))
        );
        const data: Essay[][] = await Promise.all(res.map((res) => res.json()));
        setEssays(data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchEssaysdata();
    setTitleActive(Array.from({ length: titles.length }, (): boolean => false));
  }, [titles]);

  useEffect(() => {
    const tmpEssayActive: boolean[][] = essays.map(
      (subArray: Essay[]): boolean[] =>
        subArray.map((essay: Essay, index: number): boolean =>
          index == 0 ? true : false
        )
    ); // essay 목록에 해당하는 boolean 배열 만들기
    setMyEssayActive(tmpEssayActive);
  }, [essays]);
  //메인 타이틀 받아오면 그 안의 essays 받아오기
  if (myEssayActive.length == 0) {
    return <Fragment></Fragment>;
  }

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
      {titles.map((title: { id: number; title: string }, index: number) => {
        return (
          <Fragment key={title.id}>
            <div
              className={`w-full flex flex-row border border-black rounded-lg p-2 min-h-10 justify-between ${index === 0 ? "mt-12" : "mt-2"}`}
            >
              <input
                ref={inputRef}
                placeholder={`${index + 1}. ${title.title}`}
                className="w-full mr-10 outline-none"
              />
              <div className="flex flex-row gap-6 mr-4 text-lg">
                <button onClick={makeCanEditHandler}>
                  <FaPen />
                </button>
                <button onClick={() => dropDownClickHandler(index)}>
                  {titleActive[index] ? <FaChevronUp /> : <FaChevronDown />}
                </button>
              </div>
            </div>
            <div className={`${titleActive[index] ? "" : "hidden"}`}>
              <div className="flex flex-row m-1">
                {essays[index].map((essay: Essay, essayIndex: number) => {
                  const isActive: boolean =
                    myEssayActive[index][essayIndex] || false;
                  return (
                    <button
                      type="button"
                      onClick={() => clickEssayHandler(index, essayIndex)}
                      key={essayIndex}
                      className={`ml-6 min-w-12  ${isActive ? "text-f5green-300" : ""}`}
                    >
                      {essay.company}
                    </button>
                  );
                })}
              </div>
              {essays[index].map((essay: Essay, essayIndex: number) => {
                if (!myEssayActive[index][essayIndex]) {
                  return;
                }
                return (
                  <Fragment key={essayIndex}>
                    <div className="relative flex flex-row w-full border text-sm  border-black border-b-white rounded-t-lg mt-2 p-2 min-h-16">
                      <p className="mr-10">{essay.title}</p>
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
          </Fragment>
        );
      })}
      <button
        onClick={() => setIsModalOpen(true)}
        className="items-center border border-black rounded-lg w-full flex flex-row gap-2 justify-center p-2 min-h-10 mt-2"
      >
        <div className="text-f5green-300">
          <FaPlus />
        </div>
        <div>과거 자소서 추가</div>
      </button>
      <Modal open={isModalOpen}>
        <div className="flex flex-col flex-wrap">
          <div className="text-xl font-medium text-center ">
            🖋 추가할 자소서 항목을 작성해주세요 🖋
          </div>

          <form>
            <input placeholder="추가할 자소서 항목을 작성해주세요" />
            <div className="flex justify-center mt-5 ">
              <button
                onClick={() => setIsModalOpen(false)}
                className="px-12 py-2 mr-6 text-neutral-100 text-sm font-semibold rounded-md bg-f5red-350  hover:bg-f5red-300 ring-1 ring-inset ring-f5red-700/10"
              >
                취소하기
              </button>
              <button
                type="submit"
                className="px-12 py-2 text-neutral-100 text-sm font-semibold rounded-md bg-f5green-350  hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10"
              >
                추가하기
              </button>
            </div>
          </form>
        </div>
      </Modal>
    </div>
  );
}
