import React, { forwardRef } from 'react';
import { cn } from '@/lib/utils'; // Assumindo que você usa shadcn/tailwind utils

interface FormInputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  icon?: string;
  error?: string;
}

export const FormInput = forwardRef<HTMLInputElement, FormInputProps>(
  ({ label, icon, error, className, ...props }, ref) => {
    return (
      <div className="w-full">
        <label className="text-[#1E255E] font-medium text-sm block mb-3">
          {label}
        </label>
        <div
          className={cn(
            'justify-center border flex w-full flex-col font-normal px-[26px] py-[18px] rounded-[30px] border-solid border-[rgba(30,37,94,0.50)] transition-colors focus-within:border-[#0061FE] focus-within:ring-2 focus-within:ring-[#0061FE] focus-within:ring-opacity-20',
            error && 'border-red-500 focus-within:border-red-500 focus-within:ring-red-500',
            className
          )}
        >
          <div className="flex w-full max-w-full items-stretch gap-2">
            {icon && (
              <img
                src={icon}
                alt=""
                className="aspect-[1] object-contain w-6 shrink-0"
                aria-hidden="true"
              />
            )}
            <input
              ref={ref}
              className="text-[#1E255E] grow shrink w-full bg-transparent border-none outline-none placeholder:text-[#1E255E] placeholder:opacity-30"
              {...props}
            />
          </div>
        </div>
        {error && (
          <p className="text-red-500 text-sm mt-1" role="alert">
            {error}
          </p>
        )}
      </div>
    );
  }
);

FormInput.displayName = 'FormInput';