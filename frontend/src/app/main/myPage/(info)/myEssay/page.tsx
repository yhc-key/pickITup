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
import useEssayStore from "@/store/essayStore";
import { redirect } from "next/navigation";
interface Essay {
  company: string;
  title: string;
  id: number;
  content: string;
}

interface Title {
  id: number;
  title: string;
}

const apiAddress = "https://spring.pickITup.online/self/main";

const dummyTitles: Title[] = [];
const myEssays: Essay[][] = [[]]; // essay 목록 가져오기

const token: string =
  "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZSI6IlJPTEVfVVNFUiIsImV4cCI6MTcxMDkwNTM0Nn0.A-pDxNwD38jJhVGMt-NCbk1ykeZ79DrT57rq946pDE8";

export default function MyEssay(): JSX.Element {
  const [beforeChangeTitle, setBeforeChangeTitle] = useState<Title>(
    dummyTitles[0]
  );
  const nowClickEssay = useRef({
    titleId: 0,
    essayId: 0,
  });

  const [isAddModalOpen, setIsAddModalOpen] = useState<boolean>(false);
  const [isChangeModalOpen, setIsChangeModalOpen] = useState<boolean>(false);
  const [isEssayChangeModalOpen, setIsEssayChangeModalOpen] =
    useState<boolean>(false);
  const [myEssayActive, setMyEssayActive] = useState<boolean[][]>([]); //서브 타이틀 나오면서 뜨는 회사 명중 클릭된 것
  const [titleActive, setTitleActive] = useState<boolean[]>([]); //타이틀 액티브 되었다는 것은 드롭다운이 내려간다는 뜻
  const [titles, setTitles] = useState(dummyTitles);
  const [essays, setEssays] = useState<Essay[][]>(myEssays);
  const [titleValidate, setTitleValidate] = useState<boolean>(true);

  const essayTitleAddRef = useRef<HTMLInputElement>(null);
  const essayTitleChangeRef = useRef<HTMLInputElement>(null);
  const essayChangeRef = useRef<HTMLTextAreaElement>(null);

  const makeCanEditHandler = (index: number) => {
    setBeforeChangeTitle(titles[index]);
    setIsChangeModalOpen(true);
  };

  const makeCanEditTitleContentHandler = (index: number) => {};

  const dropDownClickHandler: (index: number) => void = (
    index: number
  ): void => {
    const arr: boolean[] = [...titleActive];
    arr[index] = !arr[index];
    setTitleActive(arr);
    return;
  }; // 클릭시 titleActive true false 바꾸기

  const clickEssayHandler = (essayIndex: number, companyIndex: number) => {
    const tmpEssays: boolean[][] = cloneDeep(myEssayActive);
    tmpEssays[essayIndex].forEach(
      (value: boolean, index: number) => (tmpEssays[essayIndex][index] = false)
    );
    tmpEssays[essayIndex][companyIndex] = true;
    setMyEssayActive(tmpEssays);
  };

  const clickAddEssayHandler = () => {};

  const makeChangeTitle = async (
    id: number,
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    event.preventDefault();
  };

  const deleteEssayHandler = async (titleId: number, essayId: number) => {
    try {
      await fetch(`${apiAddress}/${titleId}/sub/${essayId}`, {
        method: "DELETE",
      });

      // window.location.reload();
      // 혹시 전역 객체가 window가 아니어서 문제생길수있음 nodejs 환경에서는 서버 띄우고 글로벌 검토 요망
    } catch (error) {
      console.error(error);
    }
    redirect("/myPage/myEssay");
  };

  const addSubmitHandler = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!essayTitleAddRef.current) return;
    if (essayTitleAddRef.current.value.trim() == "") {
      setTitleValidate(false);
      return;
    }
    setTitleValidate(true);
    try {
      await fetch(apiAddress, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
        body: JSON.stringify({ title: essayTitleAddRef.current.value.trim() }),
      });

      window.location.reload();
    } catch (error) {
      console.error(error);
    }
  };

  const changeSubmitHandler = async (
    event: React.FormEvent<HTMLFormElement>,
    id: number
  ) => {
    event.preventDefault();
    if (!essayTitleAddRef.current) return;
    if (essayTitleAddRef.current.value.trim() == "") {
      setTitleValidate(false);
      return;
    }
    setTitleValidate(true);
    try {
      await fetch(`${apiAddress}/${id}`, {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ title: essayTitleAddRef.current.value.trim() }),
      });

      window.location.reload();
    } catch (error) {
      console.error(error);
    }
  };

  const changeEssaySubmitHandler = (titleId: number, essayId: number) => {
    setIsEssayChangeModalOpen(true);
    nowClickEssay.current = { titleId, essayId };
  };

  const changeEssay = async () => {
    let changeE = {};
    if (
      essayTitleChangeRef.current?.value.trim() === "" &&
      essayChangeRef.current?.value.trim() === ""
    ) {
      window.location.reload();
    } else if (essayTitleChangeRef.current?.value.trim() === "") {
      changeE = { content: essayChangeRef.current?.value };
    } else if (essayChangeRef.current?.value.trim() === "") {
      changeE = { title: essayTitleChangeRef.current?.value };
    } else {
      changeE = {
        content: essayChangeRef.current?.value,
        title: essayTitleChangeRef.current?.value,
      };
    }

    try {
      await fetch(
        `${apiAddress}/${nowClickEssay.current.titleId}/sub/${nowClickEssay.current.essayId}`,
        {
          method: "PATCH",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(changeE),
        }
      );
      window.location.reload();
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    const essayListfetchData = async () => {
      try {
        const res: Response = await fetch(apiAddress, {
          headers: {
            Authorization: "Bearer " + token,
          },
        });
        if (!res.ok) {
          throw new Error("Failed to fetch data");
        }
        const jsonData = await res.json();
        setTitles(jsonData.response);
        useEssayStore.getState().updateEssayTitles(jsonData.response);
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
        const data: Essay[][] = await Promise.all(
          res.map(async (res) => {
            const jsonData = await res.json();
            return jsonData.response;
          })
        );
        setEssays(data);
        setTitleActive(
          Array.from({ length: titles.length }, (): boolean => false)
        );
      } catch (error) {
        console.error(error);
      }
    };
    fetchEssaysdata();
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

  return (
    <div className="relative w-full pt-3 pr-3">
      <Link
        href="/myPage/addEssay"
        className="absolute flex flex-row items-center gap-2 px-4 py-2 border border-black rounded-lg right-3"
      >
        <div className="text-f5green-300">
          <FaPlus />
        </div>
        <div>과거 자소서 추가</div>
      </Link>
      {titles.length !== 0 &&
        titles.map((title: Title, index: number) => {
          return (
            <Fragment key={title.id}>
              <div
                className={`w-full flex flex-row border border-black rounded-lg p-2 min-h-10 justify-between ${index === 0 ? "mt-12" : "mt-2"}`}
              >
                <input
                  value={`${index + 1}. ${title.title}`}
                  onChange={(e) => {
                    makeChangeTitle(title.id, e);
                  }}
                  disabled
                  className="w-full mr-10 outline-none"
                />
                <div className="flex flex-row gap-6 mr-4 text-lg">
                  <button onClick={() => makeCanEditHandler(index)}>
                    <FaPen />
                  </button>
                  <button onClick={() => dropDownClickHandler(index)}>
                    {titleActive[index] ? <FaChevronUp /> : <FaChevronDown />}
                  </button>
                </div>
              </div>
              <div className={`${titleActive[index] ? "" : "hidden"}`}>
                <div className="flex flex-row m-1">
                  {essays[index]?.map((essay: Essay, essayIndex: number) => {
                    const isActive: boolean =
                      myEssayActive[index]?.[essayIndex] || false;
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
                {essays[index]?.map((essay: Essay, essayIndex: number) => {
                  if (!myEssayActive[index]?.[essayIndex]) {
                    return;
                  }
                  return (
                    <Fragment key={essayIndex}>
                      <div className="relative flex flex-row w-full p-2 mt-2 text-sm border border-black rounded-t-lg border-b-white min-h-16">
                        <p className="mr-10">{essay.title}</p>
                        <button
                          type="button"
                          onClick={() => deleteEssayHandler(title.id, essay.id)}
                          className="absolute m-2 text-2xl right-2"
                        >
                          <FaTrash />
                        </button>
                      </div>
                      <div className="relative flex flex-row w-full p-2 text-sm border border-black rounded-b-lg min-h-40">
                        <p>{essay.content}</p>
                        <button
                          type="button"
                          onClick={() =>
                            changeEssaySubmitHandler(title.id, essay.id)
                          }
                          className="absolute bottom-0 m-3 text-lg right-2"
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
        onClick={() => setIsAddModalOpen(true)}
        className={`flex flex-row items-center justify-center w-full gap-2 p-2 ${titles.length === 0 ? "mt-12 " : "mt-2"} border border-black rounded-lg min-h-10`}
      >
        <div className="text-f5green-300">
          <FaPlus />
        </div>
        <div>자소서 항목 추가</div>
      </button>
      <Modal open={isAddModalOpen}>
        <div className="flex flex-col flex-wrap h-min-[400px]">
          <div className="text-xl font-medium text-center ">
            🖋 추가할 자소서 항목을 작성해주세요 🖋
          </div>

          <form onSubmit={addSubmitHandler}>
            <input
              type="text"
              placeholder="추가할 자소서 항목을 작성해주세요"
              className="w-[700px] max-w-[100%] p-1 h-auto mt-3"
              ref={essayTitleAddRef}
            />
            <div className="min-h-10">
              <span
                className={`text-f5red-300  ${titleValidate ? "hidden" : ""}`}
              >
                올바른 값을 입력해주세요
              </span>
            </div>
            <div className="flex justify-center mt-5 ">
              <button
                onClick={() => setIsAddModalOpen(false)}
                className="px-12 py-2 mr-6 text-sm font-semibold rounded-md text-neutral-100 bg-f5red-350 hover:bg-f5red-300 ring-1 ring-inset ring-f5red-700/10"
              >
                취소하기
              </button>
              <button
                type="submit"
                className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5green-350 hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10"
              >
                추가하기
              </button>
            </div>
          </form>
        </div>
      </Modal>
      <Modal open={isChangeModalOpen}>
        <div className="flex flex-col h-min-[400px]">
          <div className="text-xl font-medium text-center ">
            🖋 변경할 자소서 항목을 작성해주세요 🖋
          </div>

          <form
            onSubmit={(event) =>
              changeSubmitHandler(event, beforeChangeTitle.id)
            }
          >
            <span>변경 전 : </span>
            <span className="w-[700px] max-w-[100%] p-1 h-auto mt-3">
              {beforeChangeTitle?.title}
            </span>
            <hr />
            <label htmlFor="afterChangeTitle">변경 후 : </label>
            <input
              type="text"
              placeholder="변경후 자소서 항목"
              className="w-[700px] max-w-[100%] p-1 h-auto mt-3"
              ref={essayTitleAddRef}
              id="afterChangeTitle"
            />
            <div className="min-h-10">
              <span
                className={`text-f5red-300  ${titleValidate ? "hidden" : ""}`}
              >
                올바른 값을 입력해주세요
              </span>
            </div>
            <div className="flex justify-center mt-5 ">
              <button
                onClick={() => setIsChangeModalOpen(false)}
                className="px-12 py-2 mr-6 text-sm font-semibold rounded-md text-neutral-100 bg-f5red-350 hover:bg-f5red-300 ring-1 ring-inset ring-f5red-700/10"
              >
                취소하기
              </button>
              <button
                type="submit"
                className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5green-350 hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10"
              >
                변경하기
              </button>
            </div>
          </form>
        </div>
      </Modal>
      <Modal open={isEssayChangeModalOpen}>
        <div className="flex flex-col h-min-[800px]">
          <div className="text-xl font-medium text-center ">
            🖋 자소서 변경하기 🖋
          </div>

          <div>
            <label htmlFor="inputTitle" className="font-bold">
              자기소개서 항목
            </label>
            <input
              type="text"
              placeholder="빌 경우 원래 항목이 들어갑니다"
              className="w-[700px] min-h-16 p-1 h-auto text-sm focus:outline-f5green-300 my-3 "
              ref={essayTitleChangeRef}
              id="inputTitle"
            ></input>
            <label htmlFor="inputEssay" className="font-bold ">
              자기소개서 내용
            </label>
            <textarea
              placeholder="빌 경우 원래 내용이 들어갑니다."
              className="w-[700px] max-w-[100%] p-1 h-auto mt-3 min-h-40 text-sm my-3 focus:outline-f5green-300 text-start"
              ref={essayChangeRef}
              id="inputEssay"
            />

            <div className="flex justify-center mt-5">
              <button
                onClick={() => setIsEssayChangeModalOpen(false)}
                className="px-12 py-2 mr-6 text-sm font-semibold rounded-md text-neutral-100 bg-f5red-350 hover:bg-f5red-300 ring-1 ring-inset ring-f5red-700/10"
              >
                취소하기
              </button>
              <button
                type="button"
                onClick={() => changeEssay()}
                className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5green-350 hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10"
              >
                변경하기
              </button>
            </div>
          </div>
        </div>
      </Modal>
    </div>
  );
}
