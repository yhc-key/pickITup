"use client";
import { useState, useEffect } from "react";
import Image from "next/image";
import Link from "next/link";
import { TiTimes } from "react-icons/ti";
import Modal from "@/components/modal2";
import { techDataMap } from "@/data/techData";
import { techData2, techInfos, techAll } from "@/data/techData";
import AllSearchBar from "@/components/AllSearchBar";
import useAuthStore, { AuthState } from "@/store/authStore";
import CheckExpire from "@/data/checkExpire";
import { useMediaQuery } from "react-responsive";

interface TechSelectMyPageProps {
  onclose: () => void;
  open: boolean;
}
export default function TechSelectMyPage({
  onclose,
  open,
}: TechSelectMyPageProps) {
  const [isModalOpen, setIsModalOpen] = useState<boolean>(false);
  const [pickTech, setPickTech] = useState<string[]>([]);
  const setKeywords: (newKeywords: string[]) => void = useAuthStore(
    (state: AuthState) => state.setKeywords
  );

  const isMobile = useMediaQuery({
    query: "(max-width:480px)",
  });

  useEffect(() => {
    if (open) {
      setIsModalOpen(true);
    } else setIsModalOpen(false);
  }, []);
  useEffect(() => {
    if (open) {
      setIsModalOpen(true);
    } else setIsModalOpen(false);
  }, [open]);
  useEffect(() => {
    CheckExpire();
    const authid = sessionStorage.getItem("authid");
    const token = sessionStorage.getItem("accessToken");
    if (authid !== null) {
      fetch("https://spring.pickitup.online/users/keywords", {
        method: "GET",
        headers: {
          Authorization: "Bearer " + token,
        },
      })
        .then((res) => res.json())
        .then((res) => {
          console.log(res);
          setPickTech(res.response.keywords);
          if (open) {
            setIsModalOpen(true);
          } else {
            setIsModalOpen(false);
          }
        });
    }
  }, [open]);

  const clickSide = () => {
    modalCloseHandler();
  };
  // 기술스택 추가 함수
  const techAddHandler = (tech: string): void => {
    if (!pickTech.includes(tech)) {
      setPickTech([...pickTech, tech]);
    }
  };
  //기술스택 제거 함수
  const deletePickTech = (item: string) => {
    const newTechs = pickTech.filter((tech) => tech !== item);
    setPickTech(newTechs);
  };
  // 모달 닫는 함수
  const modalCloseHandler = (): void => {
    // setPickTech([]);
    setIsModalOpen(false);
    onclose();
  };

  const setMyTech = (): void => {
    CheckExpire();
    const authid = sessionStorage.getItem("authid");
    const token = sessionStorage.getItem("accessToken");
    if (authid === null) return;
    fetch("https://spring.pickitup.online/users/keywords", {
      method: "GET",
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((res) => res.json())
      .then((res) => {
        const techIds: number[] = [];
        fetch("https://spring.pickitup.online/keywords/map", {
          method: "GET",
        })
          .then((res) => res.json())
          .then((res) => {
            for (const tech of pickTech) {
              const techId = res.response[tech];
              if (techId !== undefined) {
                techIds.push(techId);
              }
            }
            fetch(`https://spring.pickitup.online/users/keywords`, {
              method: "PATCH",
              headers: {
                "Content-Type": "application/json",
                Authorization: "Bearer " + token,
              },
              body: JSON.stringify({
                keywords: techIds,
              }),
            })
              .then((res) => res.json())
              .then((res) => {
                console.log(res);
                setKeywords(pickTech);
                sessionStorage.setItem("keywords", JSON.stringify(pickTech));
              });
          });
      });
  };
  const [showSuggestions, setShowSuggestions] = useState(true);
  const handleCloseSuggestions = () => {
    setShowSuggestions(false);
  };
  const handleOpenSuggestions = () => {
    setShowSuggestions(true);
  };

  return (
    <div>
      {/* <button onClick={(): void => setIsModalOpen(true)}>
      </button> */}
      {/* { isModalOpen && */}
      <Modal
        open={isModalOpen}
        clickSide={clickSide}
        size={`min-h-[50%] ${isMobile ? "w-full px-1 py-5" : "w-8/12 px-20 py-12"}`}
      >
        <div className="flex flex-col items-center h-1/2">
          <div className="mb-5 text-xl font-medium text-center">
            관심 기술 스택(영어)을 선택해주세요
          </div>
          <div className="z-50 py-2 flex items-center justify-center">
            <AllSearchBar
              words={techAll}
              onSelect={techAddHandler}
              showSuggestions={showSuggestions}
              onCloseSuggestions={handleCloseSuggestions}
              onOpenSuggestions={handleOpenSuggestions}
            ></AllSearchBar>
          </div>

          <div className="flex flex-wrap items-center justify-center text-sm text-center z-40 min-h-12 mb-10">
            {pickTech.map((item: string, index: number) => {
              const skillImage = item.replace(/\s/g, "");
              return (
                <div
                  key={index}
                  className="flex items-center justify-center py-1 pr-2 relative border-2 border-f5green-300 rounded-2xl text-xs p-2 mx-2 my-1 min-h-6 hover:transition-all hover:scale-105 hover:ease-in"
                >
                  {techData2.includes(item) ? (
                    <Image
                      src={`/images/ITUlogo.png`}
                      alt={item}
                      width={20}
                      height={20}
                      className="inline-block mr-1 "
                    />
                  ) : (
                    <Image
                      src={`/images/techLogo/${skillImage}.png`}
                      alt={item}
                      width={20}
                      height={20}
                      className="inline-block mr-1"
                    />
                  )}
                  {item}
                  <div
                    className="ml-1 cursor-pointer"
                    onClick={() => deletePickTech(item)}
                  >
                    <TiTimes color="red" size="15" />
                  </div>
                </div>
              );
            })}
          </div>
          {/* <div className="flex flex-wrap justify-center gap-2 mt-1">
          </div> */}
          <div className="fixed bottom-4 flex justify-center items-center gap-8">
            <button
              onClick={modalCloseHandler}
              className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5red-350 hover:bg-f5red-300 ring-1 ring-inset ring-f5red-700/10"
            >
              나중에 하기
            </button>
            <div
              onClick={() => {
                setMyTech(), modalCloseHandler();
              }}
            >
              <button className="px-12 py-2 text-sm font-semibold rounded-md text-neutral-100 bg-f5green-350 hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10">
                저장하기
              </button>
            </div>
          </div>
        </div>
      </Modal>
      {/* }  */}
    </div>
  );
}
