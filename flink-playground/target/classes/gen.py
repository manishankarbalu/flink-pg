import csv
import random
import datetime

# Parameters
num_entries = 1000000  # Number of entries
num_products = 1000    # Number of unique product IDs
output_file = 'generated_sales_data.csv'

# Generate random product IDs
product_ids = [f'{i:04}' for i in range(1, num_products + 1)]

# Generate a random datetime
def random_date(start, end):
    return start + datetime.timedelta(
        seconds=random.randint(0, int((end - start).total_seconds())))

# Date range for the data
start_date = datetime.datetime(2024, 1, 1)
end_date = datetime.datetime(2024, 12, 31)

# Random transaction generation
def generate_transaction(transaction_id):
    product_id = random.choice(product_ids)
    quantity = random.randint(1, 10)  # Random quantity between 1 and 10
    price = round(random.uniform(10, 100), 2)  # Random price between $10 and $100
    timestamp = random_date(start_date, end_date).strftime('%Y-%m-%d %H:%M:%S')
    return [transaction_id, product_id, quantity, price, timestamp]

# Generate and write the data to CSV
with open(output_file, mode='w', newline='') as file:
    writer = csv.writer(file)
    writer.writerow(['transaction_id', 'product_id', 'quantity', 'price', 'timestamp'])  # Header

    for transaction_id in range(1, num_entries + 1):
        writer.writerow(generate_transaction(transaction_id))

print(f"{num_entries} entries generated and saved to {output_file}")
