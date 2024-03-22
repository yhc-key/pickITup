"use client";
import { useState } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { IoChevronBackSharp } from "react-icons/io5";

import Modal from "@/components/modal";

export default function BackBtn() {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const router = useRouter();

  // 모달 닫는 함수
  const modalCloseHandler = (): void => {
    setIsModalOpen(false);
  };

  const goBackHandler = (): void => {
    router.back();
  };
  return (
    <div>
      <button onClick={() => setIsModalOpen(true)}>
        <IoChevronBackSharp size={26} />
      </button>
      <Modal open={isModalOpen}>
        <div className="flex flex-col flex-wrap">
          <div className="mb-5 text-xl font-semibold text-center">
            게임을 종료하시겠습니까 ?
          </div>
          <div className="mb-5 font-medium text-center text-ml">
            지금 종료하면 결과가 기록되지 않습니다 💦
          </div>
          <div className="flex flex-wrap justify-center gap-2 mt-3"></div>
          <div className="flex flex-col items-center justify-center mt-5">
            <div>
              <Link href={`/game`}>
                <button className="px-20 py-3 mb-5 text-sm font-semibold rounded-md text-neutral-100 bg-f5red-350 hover:bg-f5red-300 ring-1 ring-inset ring-f5red-700/10">
                  종료하기
                </button>
              </Link>
            </div>
            <div>
              <button
                onClick={modalCloseHandler}
                className="px-20 py-3 text-sm font-semibold rounded-md text-neutral-100 bg-f5green-350 hover:bg-f5green-300 ring-1 ring-inset ring-f5green-700/10"
              >
                취소하기
              </button>
            </div>
          </div>
        </div>
      </Modal>
    </div>
  );
}
